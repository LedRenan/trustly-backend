package com.trustly.backend.github.messages;

/**
 * <B> Enum with application messages. </B>
 *
 * @author renan.picchi
 */
public enum ApplicationMessages {

   INVALID_URL("github.message.invalid-url"),

   CANT_READ_GITHUB_URL("github.message.cant-read-url"),

   CANT_CALCULATE_FILE_SIZE("github.message.cant-calucate-file-size"),

   CANT_DEFINE_FILE_EXTENSION("github.message.cant-define-file-extension");

   private final String key;

   private ApplicationMessages(String key) {
      this.key = key;
   }

   public String getKey() {
      return this.key;
   }
}