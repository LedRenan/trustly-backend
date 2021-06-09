package com.trustly.backend.github.api.results;

import java.math.BigInteger;

import lombok.Getter;

/**
 * <B> Class for represents a result from a Github repository summary. </B>
 *
 * @author renan.picchi
 */
@Getter
public final class FileResult {

   private final String extension;
   private BigInteger   count = BigInteger.ZERO;
   private BigInteger   lines = BigInteger.ZERO;
   private BigInteger   bytes = BigInteger.ZERO;

   FileResult(String extension) {
      super();
      this.extension = extension;
   }

   void addCountFile() {
      this.count = this.count.add(BigInteger.ONE);
   }

   void addLinesFile(BigInteger value) {
      this.lines = this.lines.add(value);
   }

   void addBytesFile(BigInteger value) {
      this.bytes = this.bytes.add(value);
   }
}