package com.trustly.backend.github.api;

import com.trustly.backend.github.api.github.GithubEnum;

/**
 * <B> Interface for a Github repository analyzer. </B>
 *
 * @author renan.picchi
 *
 * @param <T>
 *           represents the object will be returned on the {@link #getRepositorySummary(String, String)}
 */
public interface GithubRepositoryAnalyzable<T> {

   /**
    * <B> Returns the Github repository summary. </B>
    *
    * @param user
    *           Github user name.
    * @param repository
    *           Github repository name.
    * @return object represents a summary from Github repository.
    */
   public T getRepositorySummary(String user, String repository);

   /**
    * <B> Returns Github URL concatenated with the user and repository parameters. </B>
    *
    * @param user
    *           Github user name.
    * @param repository
    *           Github repository name.
    * @return Github URL.
    */
   public default String getGithubUrl(String user, String repository) {
      return GithubEnum.URL.value().concat("/").concat(user).concat("/").concat(repository);
   }

   /**
    * <B> Returns Github URL concatenated with the suffix parameter. </B>
    *
    * @param suffix
    *           URL.
    * @return Github URL.
    */
   public default String getGithubUrl(String suffix) {
      return GithubEnum.URL.value().concat("/").concat(suffix);
   }

   /**
    * <B> Checks if the URL from github is a valid repository. </B>
    *
    * @param url
    *           from Github repository.
    */
   public void checkValidRepository(String url);
}