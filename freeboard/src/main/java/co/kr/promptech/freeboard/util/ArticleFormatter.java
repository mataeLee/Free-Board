package co.kr.promptech.freeboard.util;

import co.kr.promptech.freeboard.dto.ArticleDetailDTO;
import co.kr.promptech.freeboard.dto.ArticleSummaryDTO;
import co.kr.promptech.freeboard.model.Article;

public class ArticleFormatter {
    public static ArticleSummaryDTO toSummaryDTO(Article article){
        return ArticleSummaryDTO
                .builder()
                .id(article.getId())
                .title(article.getTitle())
                .username(article.getUser().getUsername())
                .hit(article.getHit())
                .creationDate(InstantFormatter.formatString(article.getCreationDate()))
                .build();
    }

    public static ArticleDetailDTO toDetailDTO(Article article){
        return ArticleDetailDTO.builder()
                .id(article.getId())
                .content(article.getContent())
                .title(article.getTitle())
                .username(article.getUser().getUsername())
                .hit(article.getHit())
                .creationDate(InstantFormatter.formatString(article.getCreationDate()))
                .updateDate(InstantFormatter.formatString(article.getUpateDate()))
                .build();
    }
}
