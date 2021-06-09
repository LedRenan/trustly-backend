package com.trustly.backend.github.api.jsoup;

import java.math.BigInteger;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import com.trustly.backend.github.api.github.GithubEnum;
import com.trustly.backend.github.api.util.AppFileUtil;
import com.trustly.backend.github.api.util.ConverterFileUnitUtil;
import com.trustly.backend.github.exception.GithubException;
import com.trustly.backend.github.messages.ApplicationMessages;
import com.trustly.backend.github.messages.MessageUtil;

import lombok.extern.log4j.Log4j2;

/**
 * <B> Parser for get information from Github Repository using Jsoup. </B>
 *
 * @author renan.picchi
 */
@Log4j2
final class JsoupGithubRepositoryParser {

   private static final String EMTPY_CONTENT = "";

   /**
    * <B> Returns a directory content from a page Github repository. </B>
    *
    * @param document
    *           HTML document.
    * @param url
    *           Github repository URL.
    * @return object represents a directory content.
    */
   public Elements getDirectoryContent(Document document, String url) {
      Elements directory = document.select(GithubEnum.PARSER_NAVIGATION_CONTAINER.value());

      if (JsoupUtil.isNotEmpty(directory)) {
         return directory.select(GithubEnum.PARSER_DIV_BOX_ROW.value());
      }

      String message = MessageUtil.getMessage(ApplicationMessages.INVALID_URL, url);
      log.error(message);
      throw new GithubException(message);
   }

   /**
    * <B> Returns a file type from a element. </B>
    *
    * @param element
    *           HTML element.
    * @return String represents a file type.
    */
   public String getFileType(Element element) {
      Elements filesType = element.select(GithubEnum.PARSER_SVG.value());

      if (JsoupUtil.isNotEmpty(filesType)) {
         return filesType.get(0).attributes().get(GithubEnum.PARSER_ARIA_LABEL.value());
      }

      return EMTPY_CONTENT;
   }

   /**
    * <B> Return a URL from a HTML element. </B>
    *
    * @param element
    *           HTML element.
    * @return URL from a HTML element.
    */
   public String getUrl(Element element) {
      Elements filesType = element.select(GithubEnum.PARSER_SVG.value());

      if (JsoupUtil.isNotEmpty(filesType)) {
         return element.select(GithubEnum.PARSER_JS_NAVIGATION_OPEN.value())
                       .get(0)
                       .attributes()
                       .get(GithubEnum.PARSER_HREF.value());
      }

      return EMTPY_CONTENT;
   }

   /**
    * <B> Returns lines count from file. </B>
    *
    * @param document
    *           HTML document.
    * @param url
    *           Github repository URL.
    * @return lines number from file.
    */
   public BigInteger getLinesNumber(Document document, String url) {
      Elements elements = document.select(GithubEnum.PARSER_DIV_TEXT_MONO.value());

      if (JsoupUtil.isNotEmpty(elements)) {
         String content = elements.get(0).html();

         int indexOf = content.trim().indexOf(GithubEnum.PARSER_LINE.value());

         if (indexOf >= 0) {
            return new BigInteger(content.substring(0, indexOf).trim());
         }
      }

      return BigInteger.ZERO;
   }

   /**
    * <B> Returns size from file. </B>
    *
    * @param document
    *           HTML document.
    * @param url
    *           Github repository URL.
    * @return size from file.
    */
   public BigInteger getFileSize(Document document, String url) {
      Elements elements = document.select(GithubEnum.PARSER_DIV_TEXT_MONO.value());

      if (JsoupUtil.isNotEmpty(elements)) {
         List<Node> childNodes = elements.get(0).childNodes();
         String fileSize = null;

         if (childNodes.size() == 3) {
            fileSize = childNodes.get(2).toString();
         }
         else {
            String html = elements.get(0).html();
            String parserSpan = GithubEnum.PARSER_SPAN.value();
            int indexOf = html.lastIndexOf(parserSpan);

            if (indexOf >= 0) {
               fileSize = html.substring(indexOf + parserSpan.length());
            }
            else {
               fileSize = html;
            }
         }

         if (fileSize != null) {
            return ConverterFileUnitUtil.convert(fileSize.trim());
         }
      }

      String message = MessageUtil.getMessage(ApplicationMessages.CANT_CALCULATE_FILE_SIZE, url);
      log.error(message);
      throw new GithubException(message);
   }

   /**
    * <B> Returns extension from file. </B>
    *
    * @param document
    *           HTML document.
    * @param url
    *           Github repository URL.
    * @return extension from file.
    */
   public String getFileExtension(Document document, String url) {
      Elements elements = document.select(GithubEnum.PARSER_STRONG_FINAL_PATH.value());

      if (JsoupUtil.isNotEmpty(elements)) {
         List<Node> childNodes = elements.get(0).childNodes();

         if (JsoupUtil.isNotEmpty(childNodes)) {
            return AppFileUtil.getExtension(childNodes.get(0).toString());
         }
      }

      String message = MessageUtil.getMessage(ApplicationMessages.CANT_DEFINE_FILE_EXTENSION, url);
      log.error(message);
      throw new GithubException(message);
   }
}