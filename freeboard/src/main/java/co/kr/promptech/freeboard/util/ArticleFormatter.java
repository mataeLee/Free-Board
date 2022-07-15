package co.kr.promptech.freeboard.util;

import co.kr.promptech.freeboard.dto.ArticleDetailDTO;
import co.kr.promptech.freeboard.dto.ArticleSummaryDTO;
import co.kr.promptech.freeboard.model.Article;

public class ArticleFormatter {
    public static ArticleSummaryDTO toSummaryDTO(Article article){
        try {
            return ArticleSummaryDTO
                    .builder()
                    .id(article.getId())
                    .title(article.getTitle())
                    .username(article.getUser().getUsername())
                    .summary(article.getSummary())
                    .thumbnail(article.getThumbnail())
                    .hit(article.getHit())
                    .creationDate(InstantFormatter.formatString(article.getCreationDate()))
                    .build();
        }
        catch (Exception e){
            throw e;
        }
    }

    public static ArticleDetailDTO toDetailDTO(Article article){
        try {
            return ArticleDetailDTO.builder()
                    .id(article.getId())
                    .content(article.getContent())
                    .title(article.getTitle())
                    .username(article.getUser().getUsername())
                    .userprofile(article.getUser().getProfileImage())
                    .hit(article.getHit())
                    .comments(CommentFormatter.toDTOList(article.getComments()))
                    .creationDate(InstantFormatter.formatString(article.getCreationDate()))
                    .updateDate(InstantFormatter.formatString(article.getUpateDate()))
                    .build();
        }
        catch (Exception e) {
            throw e;
        }
    }
}
