package com.trustly.backend.github.api.util;

import org.apache.commons.io.FilenameUtils;

/**
 * <B> Util class for files. </B>
 *
 * @author renan.picchi
 */
public final class AppFileUtil {

   private AppFileUtil() {

   }

   /**
    * <B> Returns file extension from file. </B>
    *
    * @param filename
    *           file name from file.
    * @return file extension.
    */
   public static String getExtension(String filename) {
      String result = filename;

      if (filename.contains(FilenameUtils.EXTENSION_SEPARATOR_STR)) {
         result = FilenameUtils.getExtension(filename);
      }

      return result;
   }
}