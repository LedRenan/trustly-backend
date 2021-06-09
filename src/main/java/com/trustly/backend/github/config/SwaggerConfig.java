package com.trustly.backend.github.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <B> Class for Swagger configuration. </B>
 *
 * @author renan.picchi
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

   @Bean
   public Docket applicationApi() {
      return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false)
                                                    .select()
                                                    .apis(RequestHandlerSelectors.basePackage("com.trustly.backend.github.resources"))
                                                    .build()
                                                    .useDefaultResponseMessages(false)
                                                    .apiInfo(metaData());

   }

   private ApiInfo metaData() {
      return new ApiInfoBuilder().title("Github Repository Analyzer")
                                 .description("API to provide details from a Git Hub public repository")
                                 .contact(new Contact("Renan Gatte Picchi", "", "renangatte@gmail.com"))
                                 .version("1.0.0")
                                 .build();
   }
}
