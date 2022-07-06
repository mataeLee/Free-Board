package co.kr.promptech.freeboard.service;

import co.kr.promptech.freeboard.dto.CommentDTO;
import co.kr.promptech.freeboard.model.Account;
import co.kr.promptech.freeboard.model.Article;
import co.kr.promptech.freeboard.model.Comment;
import co.kr.promptech.freeboard.repository.CommentRepository;
import co.kr.promptech.freeboard.util.InstantFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public void save(CommentDTO commentDTO, Account account, Article article) {
        Comment comment = null;
        if(commentDTO.getId() != null) comment = commentRepository.findById(commentDTO.getId()).orElse(null);
        if(Objects.isNull(comment)) {
            comment = Comment.builder()
                    .content(commentDTO.getContent())
                    .user(account)
                    .article(article)
                    .build();
        }
        else{
            comment.setContent(commentDTO.getContent());
        }
        commentRepository.save(comment);
    }


    public List<CommentDTO> findByArticle(Article article) {
        List<Comment> comments = commentRepository.findAllByArticleOrderByCreationDateDesc(article);
        List<CommentDTO> commentDTOList = new ArrayList<>();

        for(Comment comment: comments){
            String creationDate = InstantFormatter.formatString(comment.getCreationDate());
//            String updateDate = InstantFormatter.formatString(comment.getUpateDate());
            String username = comment.getUser().getUsername();

            commentDTOList.add(CommentDTO.builder()
                    .id(comment.getId())
                    .content(comment.getContent())
                    .creationDate(creationDate)
                    .username(username)
                    .articleId(comment.getArticle().getId())
                    .build());
        }
        return commentDTOList;
    }

    public List<CommentDTO> findByAccount(Account user) {
        List<Comment> comments = commentRepository.findAllByUserOrderByCreationDateDesc(user);
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for(Comment comment : comments){
            commentDTOList.add(CommentDTO.builder()
                    .id(comment.getId())
                    .content(comment.getContent())
                    .creationDate(InstantFormatter.formatString(comment.getCreationDate()))
                    .username(comment.getUser().getUsername())
                    .articleId(comment.getArticle().getId())
                    .build());
        }
        return commentDTOList;
    }

    public void delete(Long id) {
        Comment comment = commentRepository.findById(id).orElse(null);
        if(Objects.isNull(comment)) throw new NullPointerException("comment is null");
        commentRepository.delete(comment);
    }
}