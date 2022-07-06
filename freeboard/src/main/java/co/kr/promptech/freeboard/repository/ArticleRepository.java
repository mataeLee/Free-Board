package co.kr.promptech.freeboard.repository;

import co.kr.promptech.freeboard.model.Account;
import co.kr.promptech.freeboard.model.Article;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;

public interface ArticleRepository extends CrudRepository<Article, Long> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE article SET hit = hit+1 WHERE id = ?1", nativeQuery = true)
    void addHitByArticleId(Long id);

    List<Article> findAllByCreationDateBetweenOrderByCreationDateDesc(Instant before, Instant after);

    List<Article> findAllByOrderByCreationDateDesc();

    List<Article> findAllByUserOrderByCreationDateDesc(Account account);
}
