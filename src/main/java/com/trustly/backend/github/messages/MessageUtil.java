package com.trustly.backend.github.messages;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * <B> Class represents a message util. </B>
 * 
 * @author renan.picchi
 */
public final class MessageUtil {

   private static ResourceBundle messageResource = ResourceBundle.getBundle("messages");
   private static final String   DEFAULT_MESSAGE = "Missing message";

   public static String getMessage(ApplicationMessages message, Object... args) {
      String result = "";

      try {
         result = messageResource.getString(message.getKey());
      }
      catch (Exception e) {
         result = DEFAULT_MESSAGE.concat(": ").concat(message.getKey());
      }

      return MessageFormat.format(result, args);
   }
}