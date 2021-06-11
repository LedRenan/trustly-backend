package com.trustly.backend.github.api.jsoup;

import java.math.BigInteger;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.trustly.backend.github.api.github.GithubFileType;
import com.trustly.backend.github.api.results.RepositorySummary;

/**
 * <B> Parser for get information from Github Repository using Jsoup. This implementation uses threads to be faster. </B>
 *
 * @author renan.picchi
 */
public final class JsoupGithubRepositoryAnalyzer extends AbstractJsoupGithubRepositoryAnalyzer<RepositorySummary> {

   private static final int                  DELAY_SCHEDULE = 300;
   private final JsoupGithubRepositoryParser parser         = new JsoupGithubRepositoryParser();

   @Override
   public RepositorySummary getRepositorySummary(String user, String repository) {
      String url = this.getGithubUrl(user, repository);
      super.checkValidRepository(url);

      RepositorySummary results = new RepositorySummary();
      Document document = null;

      try {
         document = super.getDocument(url);
      }
      catch (Throwable e) {
         // only throw the exception, because we logged in abstract class
         throw e;
      }

      startRead(results, document, url, GithubFileType.DIRECTORY);
      return results;
   }

   private void startRead(RepositorySummary results, Document document, String url, GithubFileType fileType) {
      if (document != null) {
         if (GithubFileType.DIRECTORY.equals(fileType)) {
            readDirectory(results, document, url);
            return;
         }

         if (GithubFileType.FILE.equals(fileType)) {
            readFile(results, document, url);
            return;
         }
      }
   }

   private void readDirectory(RepositorySummary results, Document document, String url) {
      Elements directoryContent = this.parser.getDirectoryContent(document, url);
      ScheduledThreadPoolExecutor executor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(50);

      for (Element content : directoryContent) {
         GithubFileType fileType = GithubFileType.get(this.parser.getFileType(content));

         if (fileType != null) {
            String elementUrl = super.getGithubUrl(this.parser.getUrl(content));
            Document elementDocument = super.getDocument(elementUrl);

            executor.schedule(() -> {
               startRead(results, elementDocument, elementUrl, fileType);
            }, DELAY_SCHEDULE, TimeUnit.MILLISECONDS);
         }
      }

      try {
         executor.shutdown();

         if (executor.awaitTermination(60, TimeUnit.MINUTES)) {
            executor.shutdownNow();
         }
      }
      catch (InterruptedException e) {
         executor.shutdownNow();
      }
   }

   private void readFile(RepositorySummary results, Document document, String url) {
      BigInteger linesNumber = this.parser.getLinesNumber(document, url);
      BigInteger fileSize = this.parser.getFileSize(document, url);
      String fileExtension = this.parser.getFileExtension(document, url);

      results.add(linesNumber, fileSize, fileExtension);
   }
}