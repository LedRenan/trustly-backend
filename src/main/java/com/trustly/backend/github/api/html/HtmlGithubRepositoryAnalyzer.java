package com.trustly.backend.github.api.html;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.trustly.backend.github.api.github.GithubFileType;
import com.trustly.backend.github.api.results.RepositorySummary;
import com.trustly.backend.github.exception.GithubException;
import com.trustly.backend.github.messages.ApplicationMessages;
import com.trustly.backend.github.messages.MessageUtil;

import lombok.extern.log4j.Log4j2;

/**
 * <B> Parser for get information from Github Repository using Html parser. </B>
 *
 * @author renan.picchi
 */
@Log4j2
public final class HtmlGithubRepositoryAnalyzer extends AbstractHtmlGithubRepositoryAnalyzer<RepositorySummary> {

   private static final int                 DELAY_SCHEDULE = 300;
   private final HtmlGithubRepositoryParser parser         = new HtmlGithubRepositoryParser();

   @Override
   public RepositorySummary getRepositorySummary(String user, String repository) {
      super.checkValidRepository(this.getGithubUrl(user, repository));

      RepositorySummary results = new RepositorySummary();

      startRead(results, this.getGithubUrl(user, repository), user, repository);
      return results;
   }

   private void startRead(RepositorySummary results, String url, String user, String repository) {
      String htmlDocument = super.getHtmlDocument(url);

      Collection<HtmlElement> htmlElements = this.parser.getElements(htmlDocument, user, repository);
      ScheduledThreadPoolExecutor executor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(10);

      htmlElements.forEach(element -> {
         executor.schedule(() -> {
            if (GithubFileType.DIRECTORY.equals(element.getType())) {
               startRead(results, super.getGithubUrl(element.getUrl()), user, repository);
               return;
            }

            if (GithubFileType.FILE.equals(element.getType())) {
               readFile(results, element.getUrl());
               return;
            }
         }, DELAY_SCHEDULE, TimeUnit.MILLISECONDS);
      });

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

   private void readFile(RepositorySummary results, String fileUrl) {
      String fileExtension = this.parser.getFileExtension(fileUrl);
      fileUrl = super.getGithubFileContentUrl(fileUrl.replaceAll("/blob", "/"));

      try {
         InputStreamReader isr = null;
         BufferedReader buffRead = null;
         URL url = new URL(fileUrl);
         URLConnection conn = url.openConnection();
         BigInteger fileSize = new BigInteger(String.valueOf(conn.getContentLength()));

         isr = new InputStreamReader(conn.getInputStream());
         buffRead = new BufferedReader(isr);
         BigInteger linesNumber = new BigInteger(String.valueOf(buffRead.lines().count()));

         results.add(linesNumber, fileSize, fileExtension);
      }
      catch (IOException e) {
         String message = MessageUtil.getMessage(ApplicationMessages.CANT_READ_GITHUB_FILE, fileUrl);
         log.error(message);
         throw new GithubException(message);
      }
   }
}