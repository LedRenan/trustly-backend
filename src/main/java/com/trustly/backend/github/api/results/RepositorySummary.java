package com.trustly.backend.github.api.results;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import lombok.ToString;

/**
 * <B> Encapsulated class with the file results groupd by file extension. </B>
 *
 * @author renan.picchi
 */
@ToString
public final class RepositorySummary {

   private final Map<String, FileResult> results = Collections.synchronizedMap(new HashMap<String, FileResult>());

   /**
    * <B> Add a result in object. </B>
    *
    * @param linesNumber
    * @param fileSize
    * @param fileExtension
    */
   public synchronized void add(BigInteger linesNumber, BigInteger fileSize, String fileExtension) {
      FileResult object = this.results.get(fileExtension);

      if (object == null) {
         object = new FileResult(fileExtension);
         this.results.put(fileExtension, object);
      }

      object.addBytesFile(fileSize);
      object.addCountFile();
      object.addLinesFile(linesNumber);
   }

   public Collection<FileResult> getResults() {
      return this.results.values();
   }
}