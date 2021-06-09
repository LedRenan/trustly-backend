package com.trustly.backend.github.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Service;

import com.trustly.backend.github.api.jsoup.JsoupGithubRepositoryAnalyzerV2;
import com.trustly.backend.github.api.results.RepositorySummary;
import com.trustly.backend.github.dto.RepositorySummaryDTO;
import com.trustly.backend.github.service.GithubRepositoryDetailService;

/**
 * <B> Jsoup Github Repository Details Service. </B>
 *
 * @author renan.picchi
 */
@Service
public class JsoupGithubRepositoryDetailsService implements GithubRepositoryDetailService<Collection<RepositorySummaryDTO>> {

   @Override
   public Collection<RepositorySummaryDTO> getGithubRepositorySummary(String user, String repository) {
      RepositorySummary repositorySummary = new JsoupGithubRepositoryAnalyzerV2().getRepositorySummary(user, repository);

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