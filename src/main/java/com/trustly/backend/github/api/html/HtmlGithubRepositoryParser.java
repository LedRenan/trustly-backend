package com.trustly.backend.github.api.html;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.io.FilenameUtils;

import com.trustly.backend.github.api.github.GithubEnum;
import com.trustly.backend.github.api.github.GithubFileType;

/**
 * <B> Parser for get information from Github Repository using Html. </B>
 *
 * @author renan.picchi
 */
public final class HtmlGithubRepositoryParser {

   /**
    * <B> Returns a collection with html url elements from a html document. </B>
    *
    * @param htmlDocument
    *           content from html.
    * @param user
    *           Github user name.
    * @param repository
    *           Github repository name.
    * @return collection with html url elements.
    */
   public Collection<HtmlElement> getElements(String htmlDocument, String user, String repository) {
      Collection<String> allURL = getAllURL(htmlDocument);
      Collection<HtmlElement> htmlElements = new ArrayList<HtmlElement>();

      final String baseUrl = user.concat("/").concat(repository);

      allURL.forEach(url -> {
         if (!url.isEmpty() && url.startsWith("/".concat(baseUrl))) {
            String baseUrlDir = baseUrl.concat("/").concat(GithubEnum.PARSER_TREE.value());
            String baseUrlFile = baseUrl.concat("/").concat(GithubEnum.PARSER_BLOB.value());
            if (url.contains(baseUrlDir) || url.contains(baseUrlFile)) {
               GithubFileType fileType = url.contains(baseUrlDir) ? GithubFileType.DIRECTORY : GithubFileType.FILE;

               htmlElements.add(new HtmlElement(fileType, url));
            }
         }
      });

      return htmlElements;
   }

   private Collection<String> getAllURL(String htmlDocument) {
      Collection<String> htmlLines = Arrays.asList(htmlDocument.split("\n"));
      Collection<String> results = new ArrayList<String>();

      htmlLines.forEach(line -> {
         int indexOfHref = line.indexOf(GithubEnum.PARSER_HREF.value());

         if (checkLineValid(line) && indexOfHref >= 0) {
            if (line.contains("Go to parent directory")) {
               System.out.println();
            }
            String substringHref = line.substring(indexOfHref);
            String link = substringHref.substring(0, substringHref.lastIndexOf("\""));

            String[] split = link.split("=\"");

            if (split.length >= 2) {
               int indexOfPreResult = split[1].indexOf("\">");
               String result = split[1];

               if (indexOfPreResult >= 0) {
                  result = result.substring(0, indexOfPreResult);
               }

               results.add(result);
            }
         }
      });

      return results;
   }

   private boolean checkLineValid(String line) {
      return !line.contains(GithubEnum.PARSER_PERMALINK.value())
             && !line.contains(GithubEnum.PARSE_TITLE_GO_TO_PARENT_DIRECTORY.value());
   }

   /**
    * <B> Returns file extension from file. </B>
    *
    * @param filename
    *           file name from file.
    * @return file extension.
    */
   public final String getFileExtension(String filename) {
      String[] split = filename.split("/");
      filename = split[split.length - 1];

      if (filename.contains(FilenameUtils.EXTENSION_SEPARATOR_STR)) {
         filename = FilenameUtils.getExtension(filename);
      }

      return filename;
   }
}