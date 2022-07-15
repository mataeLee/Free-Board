package co.kr.promptech.freeboard.util;

import co.kr.promptech.freeboard.dto.CommentDTO;
import co.kr.promptech.freeboard.model.Comment;

import java.util.ArrayList;
import java.util.List;

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

    public static List<CommentDTO> toDTOList(List<Comment> comments){
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for(Comment comment: comments){
            commentDTOList.add(toDTO(comment));
        }
        return commentDTOList;
    }
}
