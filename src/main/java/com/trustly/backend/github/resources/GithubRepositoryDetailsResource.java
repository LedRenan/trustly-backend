package com.trustly.backend.github.resources;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.trustly.backend.github.dto.GithubSummaryInputDTO;
import com.trustly.backend.github.dto.RepositorySummaryDTO;
import com.trustly.backend.github.service.impl.HtmlGithubRepositoryDetailsService;
import com.trustly.backend.github.service.impl.JsoupGithubRepositoryDetailsService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * <B> Class represents resource (controller) for Github repository details. </B>
 *
 * @author renan.picchi
 */
@RestController
@RequestMapping(value = "/api")
@CrossOrigin(origins = "*")
public class GithubRepositoryDetailsResource {

   @Autowired
   private JsoupGithubRepositoryDetailsService jsoupGithubService;

   @Autowired
   private HtmlGithubRepositoryDetailsService  htmlGithubService;

   @ApiOperation(value = "Returns a summary from Github repository with lines count, bytes amount grouped by file extension (using jsoup parser)")
   @ApiResponses(value = {@ApiResponse(code = 200, message = "Response is OK"),
                          @ApiResponse(code = 400, message = "Invalid parameters (user/repository)"),
                          @ApiResponse(code = 404, message = "Github repository not found"),
                          @ApiResponse(code = 500, message = "Internal server error")})
   @GetMapping(value = "github-repository-summary-jsoup", produces = "application/json")
   @ResponseStatus(HttpStatus.OK)
   public Collection<RepositorySummaryDTO> getRepositorySummary(@Valid GithubSummaryInputDTO githubDTO) {
      return this.jsoupGithubService.getGithubRepositorySummary(githubDTO.getUser(), githubDTO.getRepository());
   }

   @ApiOperation(value = "Returns a summary from Github repository with lines count, bytes amount grouped by file extension (using html parser)")
   @ApiResponses(value = {@ApiResponse(code = 200, message = "Response is OK"),
                          @ApiResponse(code = 400, message = "Invalid parameters (user/repository)"),
                          @ApiResponse(code = 404, message = "Github repository not found"),
                          @ApiResponse(code = 500, message = "Internal server error")})
   @GetMapping(value = "github-repository-summary-html", produces = "application/json")
   @ResponseStatus(HttpStatus.OK)
   public Collection<RepositorySummaryDTO> getHtmlRepositorySummary(@Valid GithubSummaryInputDTO githubDTO) {
      return this.htmlGithubService.getGithubRepositorySummary(githubDTO.getUser(), githubDTO.getRepository());
   }
}