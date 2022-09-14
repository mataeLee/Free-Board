package co.kr.promptech.freeboard.repository;

import co.kr.promptech.freeboard.model.Account;
import co.kr.promptech.freeboard.model.Article;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE article SET hit = hit+1 WHERE id = ?1", nativeQuery = true)
    void addHitByArticleId(Long id);

    List<Article> findAllByCreationDateBetweenOrderByCreationDateDesc(Instant before, Instant after);

    List<Article> findAllByCreationDateBetweenOrderByHitDescCreationDateDesc(Instant before, Instant after);

    List<Article> findAllByOrderByCreationDateDesc();

    List<Article> findAllByUserOrderByCreationDateDesc(Account account);

    @EntityGraph(attributePaths = {"user"})
    Optional<Article> findById(Long id);

    @EntityGraph(attributePaths = {"user"})
    Slice<Article> findSliceBy(Pageable pageable);
}