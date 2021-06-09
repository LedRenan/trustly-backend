package com.trustly.backend.github.dto;

import java.math.BigInteger;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <B> DTO represents Github repository summary. </B>
 *
 * @author renan.picchi
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RepositorySummaryDTO {

   @ApiModelProperty(notes = "File extension")
   private String     extension;

   @ApiModelProperty(notes = "Number of lines")
   private BigInteger lines;

   @ApiModelProperty(notes = "Number of files")
   private BigInteger count;

   @ApiModelProperty(notes = "Size of total of files in bytes")
   private BigInteger bytes;
}