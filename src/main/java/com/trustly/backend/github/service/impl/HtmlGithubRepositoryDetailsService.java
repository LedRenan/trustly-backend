package com.trustly.backend.github.service.impl;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.trustly.backend.github.api.html.HtmlGithubRepositoryAnalyzer;
import com.trustly.backend.github.api.results.RepositorySummary;
import com.trustly.backend.github.dto.ConverterRepositorySummaryDTO;
import com.trustly.backend.github.dto.RepositorySummaryDTO;
import com.trustly.backend.github.service.GithubRepositoryDetailService;

/**
 * <B> Html Github Repository Details Service. </B>
 *
 * @author renan.picchi
 */
@Service
public class HtmlGithubRepositoryDetailsService implements GithubRepositoryDetailService<Collection<RepositorySummaryDTO>> {

   @Override
   public Collection<RepositorySummaryDTO> getGithubRepositorySummary(String user, String repository) {
      RepositorySummary repositorySummary = new HtmlGithubRepositoryAnalyzer().getRepositorySummary(user, repository);
      return ConverterRepositorySummaryDTO.convert(repositorySummary);
   }
}