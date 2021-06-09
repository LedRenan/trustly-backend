package com.trustly.backend.github.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.trustly.backend.github.dto.RepositorySummaryDTO;
import com.trustly.backend.github.service.impl.JsoupGithubRepositoryDetailsService;

/**
 * <B> Unit test for {@link JsoupGithubRepositoryDetailsServiceMockTest}. </B>
 *
 * @author renan.picchi
 */
@SpringBootTest
public class JsoupGithubRepositoryDetailsServiceMockTest {

   @Mock
   @InjectMocks
   private JsoupGithubRepositoryDetailsService jsoupService = new JsoupGithubRepositoryDetailsService();

   @BeforeEach
   public void setMockupOutput() {
      when(jsoupService.getGithubRepositorySummary("trustly", "challenge")).thenReturn(getSummaryResponse());
   }

   @DisplayName("Test Mock Jsoup service layer - Collection size")
   public void testGetGithubRepositorySummaryCollectionSize() {
      Collection<RepositorySummaryDTO> summaryFromService = jsoupService.getGithubRepositorySummary("trustly", "challenge");
      assertEquals(summaryFromService.size(), getSummaryResponse().size());
   }

   @DisplayName("Test Mock Jsoup service layer - Bytes size")
   public void testGetGithubRepositorySummaryBytesSize() {
      Collection<RepositorySummaryDTO> summaryFromService = jsoupService.getGithubRepositorySummary("trustly", "challenge");

      assertEquals(new ArrayList<RepositorySummaryDTO>(summaryFromService).get(0).getBytes(),
                   new ArrayList<RepositorySummaryDTO>(getSummaryResponse()).get(0).getBytes());
   }

   @DisplayName("Test Mock Jsoup service layer - File extension")
   public void testGetGithubRepositorySummaryExtension() {
      Collection<RepositorySummaryDTO> summaryFromService = jsoupService.getGithubRepositorySummary("trustly", "challenge");

      assertEquals(new ArrayList<RepositorySummaryDTO>(summaryFromService).get(0).getExtension(),
                   new ArrayList<RepositorySummaryDTO>(getSummaryResponse()).get(0).getExtension());
   }

   @DisplayName("Test Mock Jsoup service layer - Lines number")
   public void testGetGithubRepositorySummaryLinesNumber() {
      Collection<RepositorySummaryDTO> summaryFromService = jsoupService.getGithubRepositorySummary("trustly", "challenge");

      assertEquals(new ArrayList<RepositorySummaryDTO>(summaryFromService).get(0).getLines(),
                   new ArrayList<RepositorySummaryDTO>(getSummaryResponse()).get(0).getLines());
   }

   @DisplayName("Test Mock Jsoup service layer - Count number")
   public void testGetGithubRepositorySummaryCountNumber() {
      Collection<RepositorySummaryDTO> summaryFromService = jsoupService.getGithubRepositorySummary("trustly", "challenge");

      assertEquals(new ArrayList<RepositorySummaryDTO>(summaryFromService).get(0).getCount(),
                   new ArrayList<RepositorySummaryDTO>(getSummaryResponse()).get(0).getCount());
   }

   private Collection<RepositorySummaryDTO> getSummaryResponse() {
      Collection<RepositorySummaryDTO> results = new ArrayList<RepositorySummaryDTO>();

      results.add(RepositorySummaryDTO.builder()
                                      .bytes(new BigInteger("10"))
                                      .extension("java")
                                      .lines(new BigInteger("2000"))
                                      .count(new BigInteger("6"))
                                      .build());

      results.add(RepositorySummaryDTO.builder()
                                      .bytes(new BigInteger("18"))
                                      .extension("sql")
                                      .lines(new BigInteger("4000"))
                                      .count(new BigInteger("10"))
                                      .build());

      return results;
   }
}
