package com.trustly.backend.github.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <B> Class for application configuration. </B>
 *
 * @author renan.picchi
 */
@Configuration
public class ApplicationConfig {

   @Bean
   public WebMvcConfigurer corsConfigurer() {
      return new WebMvcConfigurer() {

         @Override
         public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedMethods(RequestMethod.PUT.name(),
                                    RequestMethod.POST.name(),
                                    RequestMethod.GET.name(),
                                    RequestMethod.OPTIONS.name(),
                                    RequestMethod.DELETE.name())
                    .allowedOrigins("*")
                    .maxAge(3600);
         }
      };
   }
}
