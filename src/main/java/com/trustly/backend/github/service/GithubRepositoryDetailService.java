package com.trustly.backend.github.service;

/**
 * <B> Jsoup Util class for file unit converter. </B>
 *
 * @author renan.picchi
 *
 * @param <T>
 *           represents the object will be returned on the {@link #getGithubRepositorySummary(String, String)}
 */
public interface GithubRepositoryDetailService<T> {

   /**
    * <B> Returns the Github repository summary. </B>
    *
    * @param user
    *           Github user name.
    * @param repository
    *           Github repository name.
    * @return objects represents a summary from Github repository.
    */
   public T getGithubRepositorySummary(String user, String repository);
}