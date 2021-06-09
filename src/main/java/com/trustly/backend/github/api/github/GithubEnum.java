package com.trustly.backend.github.api.github;

/**
 * <B> Enum for a special keys Github values. </B>
 *
 * @author renan.picchi
 */
public enum GithubEnum {

   URL("https://github.com"),

   PARSER_NAVIGATION_CONTAINER("div.js-active-navigation-container"),

   PARSER_DIV_BOX_ROW("div.Box-row"),

   PARSER_SVG("svg"),

   PARSER_ARIA_LABEL("aria-label"),

   PARSER_HREF("href"),

   PARSER_STRONG_FINAL_PATH("strong.final-path"),

   PARSER_DIV_TEXT_MONO("div.text-mono"),

   PARSER_JS_NAVIGATION_OPEN("a.js-navigation-open"),

   PARSER_LINE("line"),

   PARSER_SPAN("</span>");

   private final String value;

   private GithubEnum(String value) {
      this.value = value;
   }

   public String value() {
      return this.value;
   }
}