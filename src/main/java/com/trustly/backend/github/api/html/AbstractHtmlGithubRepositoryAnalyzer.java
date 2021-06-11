package com.trustly.backend.github.api.html;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.trustly.backend.github.api.GithubRepositoryAnalyzable;
import com.trustly.backend.github.api.github.GithubEnum;
import com.trustly.backend.github.exception.GithubException;
import com.trustly.backend.github.exception.GithubRepositoryNotFoundException;
import com.trustly.backend.github.messages.ApplicationMessages;
import com.trustly.backend.github.messages.MessageUtil;

import lombok.extern.log4j.Log4j2;

/**
 * <B> Abstract Html class for a Github repository analyzer. </B>
 *
 * @author renan.picchi
 *
 * @param <T>
 *           represents the object will be returned on the {@link #getRepositorySummary(String, String)}
 */
@Log4j2
abstract class AbstractHtmlGithubRepositoryAnalyzer<T> implements GithubRepositoryAnalyzable<T> {

   private final static int MAX_POSSIBLE_ATTEMPTS = 30;
   private final static int THREAD_SLEEP_TIME     = 10000;

   protected final String getGithubFileContentUrl(String suffix) {
      return GithubEnum.FILE_CONTENT_URL.value().concat(suffix);
   }

   protected final String getHtmlDocument(String url) {
      int attemptCount = 0;

      // try to get document many times.
      while (attemptCount <= MAX_POSSIBLE_ATTEMPTS) {
         try {
            URL u = new URL(url);

            HttpURLConnection huc = (HttpURLConnection) u.openConnection();
            huc.setRequestMethod("GET");
            huc.connect();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(huc.getInputStream()));
            String line;

            StringBuilder htmlDocument = new StringBuilder();
            boolean startAppend = false;

            while ((line = bufferedReader.readLine()) != null) {
               if (!startAppend && line.contains(GithubEnum.PARSE_DIV_JS_CONTAINER_DETAILS.value())) {
                  startAppend = true;
               }

               if (startAppend) {
                  htmlDocument.append(line);
                  htmlDocument.append("\n");
               }
            }

            return htmlDocument.toString();
         }
         catch (IOException e) {
            attemptCount++;
            try {
               Thread.sleep(THREAD_SLEEP_TIME);
            }
            catch (InterruptedException e1) {}
         }
      }

      // If not possível get document, throw the exception.
      String message = MessageUtil.getMessage(ApplicationMessages.CANT_READ_GITHUB_URL, url);
      log.error(message);
      throw new GithubException(message);
   }

   @Override
   public final void checkValidRepository(String url) {
      try {
         URL u = new URL(url);
         HttpURLConnection huc = (HttpURLConnection) u.openConnection();
         huc.setRequestMethod("GET");
         huc.connect();
         if (huc.getResponseCode() == 404) {
            String message = MessageUtil.getMessage(ApplicationMessages.REPOSITORY_NOT_VALID, url);
            log.error(message);
            throw new GithubRepositoryNotFoundException(message);
         }
      }
      catch (IOException e) {
         // For any reasons, throw the exception.
         String message = MessageUtil.getMessage(ApplicationMessages.CANT_CONNECT_GITHUB_REPOSITORY, url);
         log.error(message);
         throw new GithubException(message, e);
      }
   }
}