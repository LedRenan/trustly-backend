package com.trustly.backend.github.api.jsoup;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.trustly.backend.github.api.GithubRepositoryAnalyzable;
import com.trustly.backend.github.exception.GithubException;
import com.trustly.backend.github.exception.GithubRepositoryNotFoundException;
import com.trustly.backend.github.messages.ApplicationMessages;
import com.trustly.backend.github.messages.MessageUtil;

import lombok.extern.log4j.Log4j2;

/**
 * <B> Abstract Jsoup class for a Github repository analyzer. </B>
 *
 * @author renan.picchi
 *
 * @param <T>
 *           represents the object will be returned on the {@link #getRepositorySummary(String, String)}
 */
@Log4j2
abstract class AbstractJsoupGithubRepositoryAnalyzer<T> implements GithubRepositoryAnalyzable<T> {

   private final static int MAX_POSSIBLE_ATTEMPTS = 30;
   private final static int THREAD_SLEEP_TIME     = 10000;

   /**
    * <B> Returns Document object from a URL. </B>
    *
    * @param url
    *           to get document.
    * @return {@link Document} object.
    */
   protected final Document getDocument(String url) {
      int attemptCount = 0;

      // try to get document many times.
      while (attemptCount <= MAX_POSSIBLE_ATTEMPTS) {
         try {
            return Jsoup.connect(url).get();
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