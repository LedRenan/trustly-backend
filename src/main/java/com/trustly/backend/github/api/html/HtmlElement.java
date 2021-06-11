package com.trustly.backend.github.api.html;

import com.trustly.backend.github.api.github.GithubFileType;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * <B> Represents a Html Url element. </B>
 *
 * @author renan.picchi
 */
@Getter
@EqualsAndHashCode
@ToString
public final class HtmlElement {

   private final GithubFileType type;
   private final String         url;

   public HtmlElement(GithubFileType type, String url) {
      super();
      this.type = type;
      this.url = url;
   }
}