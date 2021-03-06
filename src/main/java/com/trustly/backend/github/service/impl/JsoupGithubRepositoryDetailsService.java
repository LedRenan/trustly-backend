package com.trustly.backend.github.service.impl;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.trustly.backend.github.api.jsoup.JsoupGithubRepositoryAnalyzer;
import com.trustly.backend.github.api.results.RepositorySummary;
import com.trustly.backend.github.dto.ConverterRepositorySummaryDTO;
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
      RepositorySummary repositorySummary = new JsoupGithubRepositoryAnalyzer().getRepositorySummary(user, repository);
      return ConverterRepositorySummaryDTO.convert(repositorySummary);
   }
}