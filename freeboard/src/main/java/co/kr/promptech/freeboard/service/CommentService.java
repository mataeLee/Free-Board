package co.kr.promptech.freeboard.service;

import co.kr.promptech.freeboard.dto.ArticleSummaryDTO;
import co.kr.promptech.freeboard.dto.CommentDTO;
import co.kr.promptech.freeboard.model.Account;
import co.kr.promptech.freeboard.model.Article;
import co.kr.promptech.freeboard.model.Comment;
import co.kr.promptech.freeboard.repository.CommentRepository;
import co.kr.promptech.freeboard.util.ArticleFormatter;
import co.kr.promptech.freeboard.util.CommentFormatter;
import co.kr.promptech.freeboard.util.InstantFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
            commentDTOList.add(CommentFormatter.toDTO(comment));
        }
        return commentDTOList;
    }

    public List<CommentDTO> findByAccount(Account account) {
        List<Comment> comments = commentRepository.findAllByUserOrderByCreationDateDesc(account);
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for(Comment comment : comments){
            commentDTOList.add(CommentFormatter.toDTO(comment));
        }
        return commentDTOList;
    }

    public void delete(Long id) {
        Comment comment = commentRepository.findById(id).orElse(null);
        if(Objects.isNull(comment)) throw new NullPointerException("comment is null");
        commentRepository.delete(comment);
    }

    public Page<CommentDTO> findAllByAccountByPaginated(Account account, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Comment> comments = commentRepository.findAllByUserOrderByCreationDateDesc(account);
        List<Comment> list;

        if(comments.size() < startItem){
            list = comments;
        }
        else{
            int toIndex = Math.min(startItem + pageSize, comments.size());
            list = comments.subList(startItem, toIndex);

        }

        List<CommentDTO> res = new ArrayList<>();
        for (Comment comment : list) {
            res.add(CommentFormatter.toDTO(comment));
        }

        Page<CommentDTO> articlePage = new PageImpl<>(res, PageRequest.of(currentPage, pageSize), comments.size());
        return articlePage;
    }
}