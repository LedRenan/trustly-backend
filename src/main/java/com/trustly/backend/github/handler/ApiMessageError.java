package com.trustly.backend.github.handler;

import java.util.Collection;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;

/**
 * <B> Class represents a API message error. </B>
 *
 * @author renan.picchi
 */
@Builder
@Getter
public class ApiMessageError {

   private HttpStatus         status;
   private String             message;
   private Collection<String> errors;
}