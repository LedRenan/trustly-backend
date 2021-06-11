package com.trustly.backend.github.exception;

/**
 * <B> GitHub for not found repository. </B>
 *
 * @author renan.picchi
 */
public final class GithubRepositoryNotFoundException extends RuntimeException {

   private static final long serialVersionUID = 1L;

   public GithubRepositoryNotFoundException() {
      super();
   }

   public GithubRepositoryNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
      super(message, cause, enableSuppression, writableStackTrace);
   }

   public GithubRepositoryNotFoundException(String message, Throwable cause) {
      super(message, cause);
   }

   public GithubRepositoryNotFoundException(String message) {
      super(message);
   }

   public GithubRepositoryNotFoundException(Throwable cause) {
      super(cause);
   }
}