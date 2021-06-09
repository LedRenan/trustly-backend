package com.trustly.backend.github.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <B> DTO represents the input for a request to get Github repository summary. </B>
 *
 * @author renan.picchi
 */
@Data
@NoArgsConstructor
public final class GithubSummaryInputDTO {

   @NotNull(message = "can't be null")
   @NotBlank(message = "can't be blank")
   @ApiParam(name = "user", type = "String", value = "User from Github (https://github.com/{user}/challenge)", required = true)
   private String user;

   @NotNull(message = "can't be null")
   @NotBlank(message = "can't be blank")
   @ApiParam(name = "repository",
             type = "String",
             value = "Repository from GitHub (https://github.com/trustly/{repository})",
             required = true)
   private String repository;
}