package co.kr.promptech.freeboard.util;

import co.kr.promptech.freeboard.dto.CommentDTO;
import co.kr.promptech.freeboard.model.Comment;

public class CommentFormatter {
    public static CommentDTO toDTO(Comment comment){
        return CommentDTO
                .builder()
                .id(comment.getId())
                .username(comment.getUser().getUsername())
                .userprofile(comment.getUser().getProfileImage())
                .articleId(comment.getArticle().getId())
                .content(comment.getContent())
                .creationDate(InstantFormatter.formatString(comment.getCreationDate()))
                .build();
    }
}
