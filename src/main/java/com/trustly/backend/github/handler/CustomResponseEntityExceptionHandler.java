package com.trustly.backend.github.handler;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.trustly.backend.github.exception.GithubException;
import com.trustly.backend.github.exception.GithubRepositoryNotFoundException;

import lombok.extern.log4j.Log4j2;

/**
 * <B> Custom response entity for exception handler. </B>
 *
 * @author renan.picchi
 */
@ControllerAdvice
@Log4j2
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

   @ExceptionHandler(value = {GithubException.class, GithubRepositoryNotFoundException.class})
   protected ResponseEntity<Object> handleGithubException(Exception ex, WebRequest request) {
      Collection<String> errors = null;

      if (ex.getCause() != null) {
         errors = new ArrayList<String>();
         errors.add(ex.getCause().getLocalizedMessage());
      }

      HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

      if (ex instanceof GithubRepositoryNotFoundException) {
         status = HttpStatus.NOT_FOUND;
      }

      return handleExceptionInternal(ex,
                                     ApiMessageError.builder()
                                                    .message(ex.getLocalizedMessage())
                                                    .status(status)
                                                    .errors(errors)
                                                    .build(),
                                     new HttpHeaders(),
                                     status,
                                     request);
   }

   @Override
   protected ResponseEntity<Object> handleBindException(BindException ex,
                                                        HttpHeaders headers,
                                                        HttpStatus status,
                                                        WebRequest request) {
      BindException exception = (BindException) ex;
      Collection<String> errors = new ArrayList<String>();

      exception.getFieldErrors().forEach(error -> {
         errors.add(error.getField().concat(" ").concat(error.getDefaultMessage()));
      });


      return handleExceptionInternal(exception,
                                     ApiMessageError.builder()
                                                    .errors(errors)
                                                    .status(status)
                                                    .message(ex.getLocalizedMessage())
                                                    .build(),
                                     headers,
                                     status,
                                     request);
   }

   @ExceptionHandler(value = {Throwable.class})
   protected ResponseEntity<Object> handleAllThrowable(Exception ex,
                                                       Object body,
                                                       HttpHeaders headers,
                                                       HttpStatus status,
                                                       WebRequest request) {
      log.error(ex.getLocalizedMessage());
      return super.handleExceptionInternal(ex, body, headers, status, request);
   }

   @Override
   protected ResponseEntity<Object> handleExceptionInternal(Exception ex,
                                                            Object body,
                                                            HttpHeaders headers,
                                                            HttpStatus status,
                                                            WebRequest request) {
      log.error(ex.getLocalizedMessage());
      return super.handleExceptionInternal(ex, body, headers, status, request);
   }
}