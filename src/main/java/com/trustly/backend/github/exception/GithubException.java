package com.trustly.backend.github.exception;

/**
 * <B> GitHub exception for application. </B>
 *
 * @author renan.picchi
 */
public final class GithubException extends RuntimeException {

   private static final long serialVersionUID = 1L;

   public GithubException() {
      super();
   }

   public GithubException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
      super(message, cause, enableSuppression, writableStackTrace);
   }

   public GithubException(String message, Throwable cause) {
      super(message, cause);
   }

   public GithubException(String message) {
      super(message);
   }

   public GithubException(Throwable cause) {
      super(cause);
   }
}