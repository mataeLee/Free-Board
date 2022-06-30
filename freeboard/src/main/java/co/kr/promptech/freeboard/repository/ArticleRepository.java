package co.kr.promptech.freeboard.repository;

import co.kr.promptech.freeboard.model.Article;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.Instant;
import java.util.List;

public interface ArticleRepository extends CrudRepository<Article, Long> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update article set hit = hit+1 where id = ?1", nativeQuery = true)
    void addHitByArticleId(Long id);

    @Query(value = "SELECT * FROM article WHERE DATE(creation_date) = DATE(now())", nativeQuery = true)
    List<Article> findAllByCreatedToday();

//    List<Article> findAllByCreationDateOrderBy();
}
