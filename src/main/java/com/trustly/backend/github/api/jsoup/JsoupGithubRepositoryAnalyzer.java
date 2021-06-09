package com.trustly.backend.github.api.jsoup;

import java.io.IOException;
import java.math.BigInteger;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.trustly.backend.github.api.github.GithubFileType;
import com.trustly.backend.github.api.results.RepositorySummary;
import com.trustly.backend.github.exception.GithubException;
import com.trustly.backend.github.messages.ApplicationMessages;
import com.trustly.backend.github.messages.MessageUtil;

import lombok.extern.log4j.Log4j2;

/**
 * <B> Parser for get information from Github Repository using Jsoup. </B>
 *
 * @author renan.picchi
 */
@Log4j2
public final class JsoupGithubRepositoryAnalyzer extends AbstractJsoupGithubRepositoryAnalyzer<RepositorySummary> {

   private final JsoupGithubRepositoryParser parser = new JsoupGithubRepositoryParser();

   @Override
   public RepositorySummary getRepositorySummary(String user, String repository) {
      RepositorySummary results = new RepositorySummary();
      String url = this.getGithubUrl(user, repository);

      try {
         startRead(results, url, GithubFileType.DIRECTORY);
         return results;
      }
      catch (IOException e) {
         String message = MessageUtil.getMessage(ApplicationMessages.CANT_READ_GITHUB_URL, url);
         log.error(message);
         throw new GithubException(message);
      }
   }

   private void startRead(RepositorySummary results, String url, GithubFileType fileType) throws IOException {
      Document document = super.getDocument(url);

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

   private void readDirectory(RepositorySummary results, Document document, String url) throws IOException {
      Elements directoryContent = this.parser.getDirectoryContent(document, url);

      for (Element content : directoryContent) {
         GithubFileType fileType = GithubFileType.get(this.parser.getFileType(content));

         if (fileType != null) {
            startRead(results, getGithubUrl(this.parser.getUrl(content)), fileType);
         }
      }
   }

   private void readFile(RepositorySummary results, Document document, String url) {
      BigInteger linesNumber = this.parser.getLinesNumber(document, url);
      BigInteger fileSize = this.parser.getFileSize(document, url);
      String fileExtension = this.parser.getFileExtension(document, url);

      results.add(linesNumber, fileSize, fileExtension);
   }
}