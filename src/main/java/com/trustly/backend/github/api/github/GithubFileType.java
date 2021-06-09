package com.trustly.backend.github.api.github;

import java.util.Arrays;

/**
 * <B> Enum for differents a file type from a Github repository. </B>
 *
 * @author renan.picchi
 */
public enum GithubFileType {

   DIRECTORY,

   FILE;

   public static GithubFileType get(String value) {
      return Arrays.stream(GithubFileType.values())
                   .filter(valueFileType -> valueFileType.name().equalsIgnoreCase(value.trim()))
                   .findFirst()
                   .orElse(null);
   }
}