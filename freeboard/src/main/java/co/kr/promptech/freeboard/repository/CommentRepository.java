package co.kr.promptech.freeboard.repository;

import co.kr.promptech.freeboard.model.Account;
import co.kr.promptech.freeboard.model.Article;
import co.kr.promptech.freeboard.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    List<Comment> findAllByArticleOrderByCreationDateDesc(Article article);

    List<Comment> findAllByUserOrderByCreationDateDesc(Account user);

    @EntityGraph(attributePaths = {"user"})
    Page<Comment> findPageByArticleId(Long articleId, Pageable pageable);
}
