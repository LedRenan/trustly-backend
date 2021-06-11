package com.trustly.backend.github.dto;

import java.util.ArrayList;
import java.util.Collection;

import com.trustly.backend.github.api.results.RepositorySummary;

/**
 * <B> Converter for create the object {@link RepositorySummaryDTO} </B>
 *
 * @author renan.picchi
 */
public final class ConverterRepositorySummaryDTO {

   private ConverterRepositorySummaryDTO() {

   }

   public static final Collection<RepositorySummaryDTO> convert(RepositorySummary repositorySummary) {
      Collection<RepositorySummaryDTO> results = new ArrayList<RepositorySummaryDTO>();

      repositorySummary.getResults().forEach(result -> {
         results.add(RepositorySummaryDTO.builder()
                                         .bytes(result.getBytes())
                                         .extension(result.getExtension())
                                         .lines(result.getLines())
                                         .count(result.getCount())
                                         .build());
      });

      return results;
   }
}
