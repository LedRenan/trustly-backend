package com.trustly.backend.github.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URL;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.trustly.backend.github.dto.RepositorySummaryDTO;

/**
 * <B> Unit test for {@link GithubRepositoryDetailsResource}. </B>
 *
 * @author renan.picchi
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class GithubRepositoryDetailResourceTest {

   @LocalServerPort
   private int              port;

   @Autowired
   private TestRestTemplate restTemplate;

   @Test
   public void testJsoupSummaryOk() throws Exception {
      URL url = new URL("http://localhost:".concat(String.valueOf(this.port))
                                           .concat("/api/github-repository-summary-jsoup?user=DavidChavess&repository=aluguel-veiculos"));

      ResponseEntity<Collection<RepositorySummaryDTO>> entity = this.restTemplate.exchange(url.toURI(),
                                                                                           HttpMethod.GET,
                                                                                           null,
                                                                                           new ParameterizedTypeReference<Collection<RepositorySummaryDTO>>() {});

      Collection<RepositorySummaryDTO> summary = entity.getBody();

      assertEquals(entity.getStatusCodeValue(), 200);
      assertEquals(summary.size(), 14);

      Optional<RepositorySummaryDTO> findFirst = summary.stream()
                                                        .filter(s -> "java".equalsIgnoreCase(s.getExtension().trim()))
                                                        .findFirst();

      if (findFirst.isPresent()) {
         assertEquals("java", findFirst.get().getExtension());
      }
   }

   @Test
   @SuppressWarnings("unchecked")
   public void testJsoupInvalidParameters() throws Exception {
      URL url = new URL("http://localhost:".concat(String.valueOf(this.port))
                                           .concat("/api/github-repository-summary-jsoup?user="));
      Map<String, Object> entity = (Map<String, Object>) this.restTemplate.getForObject(url.toURI(), Object.class);

      assertEquals(entity.get("status"), "BAD_REQUEST");
   }

   @Test
   @SuppressWarnings("unchecked")
   public void testJsoupRepositoryNotValid() throws Exception {
      URL url = new URL("http://localhost:".concat(String.valueOf(this.port))
                                           .concat("/api/github-repository-summary-jsoup?user=abc&repository=trustly"));
      Map<String, Object> entity = (Map<String, Object>) this.restTemplate.getForObject(url.toURI(), Object.class);

      assertEquals(entity.get("status"), "NOT_FOUND");
   }

   @Test
   public void testHtmlSummaryOk() throws Exception {
      URL url = new URL("http://localhost:".concat(String.valueOf(this.port))
                                           .concat("/api/github-repository-summary-html?user=DavidChavess&repository=aluguel-veiculos"));

      ResponseEntity<Collection<RepositorySummaryDTO>> entity = this.restTemplate.exchange(url.toURI(),
                                                                                           HttpMethod.GET,
                                                                                           null,
                                                                                           new ParameterizedTypeReference<Collection<RepositorySummaryDTO>>() {});

      Collection<RepositorySummaryDTO> summary = entity.getBody();

      assertEquals(entity.getStatusCodeValue(), 200);
      assertEquals(summary.size(), 14);

      Optional<RepositorySummaryDTO> findFirst = summary.stream()
                                                        .filter(s -> "java".equalsIgnoreCase(s.getExtension().trim()))
                                                        .findFirst();

      if (findFirst.isPresent()) {
         assertEquals("java", findFirst.get().getExtension());
      }
   }

   @Test
   @SuppressWarnings("unchecked")
   public void testHtmlInvalidParameters() throws Exception {
      URL url = new URL("http://localhost:".concat(String.valueOf(this.port))
                                           .concat("/api/github-repository-summary-html?user="));
      Map<String, Object> entity = (Map<String, Object>) this.restTemplate.getForObject(url.toURI(), Object.class);

      assertEquals(entity.get("status"), "BAD_REQUEST");
   }

   @Test
   @SuppressWarnings("unchecked")
   public void testHtmlRepositoryNotValid() throws Exception {
      URL url = new URL("http://localhost:".concat(String.valueOf(this.port))
                                           .concat("/api/github-repository-summary-html?user=abc&repository=trustly"));
      Map<String, Object> entity = (Map<String, Object>) this.restTemplate.getForObject(url.toURI(), Object.class);

      assertEquals(entity.get("status"), "NOT_FOUND");
   }
}