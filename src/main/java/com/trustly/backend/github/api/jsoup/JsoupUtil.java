package com.trustly.backend.github.api.jsoup;

import java.util.Collection;

/**
 * <B> Util class for Jsoup components. </B>
 *
 * @author renan.picchi
 */
final class JsoupUtil {

   private JsoupUtil() {

   }

   /**
    * <B> Checks if a colletion is not empty. </B>
    *
    * @param elements
    *           to check.
    * @return true: case is not empty, otherwise, false.
    */
   public static boolean isNotEmpty(Collection<?> elements) {
      return elements != null && !elements.isEmpty();
   }
}