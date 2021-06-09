package com.trustly.backend.github.api.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;

/**
 * <B> Util class for file unit converter. </B>
 *
 * @author renan.picchi
 */
public final class ConverterFileUnitUtil {

   private ConverterFileUnitUtil() {

   }

   public static BigInteger convert(String fileSize) {
      String[] splitValues = fileSize.trim().split(" ");

      if (splitValues.length == 2) {
        return convertToBytes(new BigDecimal(splitValues[0]), splitValues[1]);
      }

      return BigInteger.ZERO;
   }

   private static BigInteger convertToBytes(BigDecimal fileSize, String unit) {
      UnitType unitType = UnitType.get(unit);
      return (fileSize.multiply(unitType.getMultiplier())).toBigInteger();
   }

   private enum UnitType {

      BYTE(BigDecimal.ONE),

      BYTES(BigDecimal.ONE),

      KB(new BigDecimal("1024")),

      MB(new BigDecimal("1024").multiply(new BigDecimal("1024"))),

      GB(new BigDecimal("1024").multiply(new BigDecimal("1024")).multiply(new BigDecimal("1024")));

      private final BigDecimal multiplier;

      private UnitType(BigDecimal multiplier) {
         this.multiplier = multiplier;
      }

      public BigDecimal getMultiplier() {
         return multiplier;
      }

      public static UnitType get(String value) {
         return Arrays.stream(UnitType.values())
                      .filter(valueFileType -> valueFileType.name().equalsIgnoreCase(value.trim()))
                      .findFirst()
                      .orElseThrow(IllegalArgumentException::new);
      }
   }
}