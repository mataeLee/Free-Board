package co.kr.promptech.freeboard.service;

import co.kr.promptech.freeboard.dto.ArticleDetailDTO;
import co.kr.promptech.freeboard.dto.ArticleSummaryDTO;
import co.kr.promptech.freeboard.model.Article;
import co.kr.promptech.freeboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public void save(Article article){
        articleRepository.save(article);
    }

    public void addHit(Long articleId){
        articleRepository.addHitByArticleId(articleId);
    }

    public List<ArticleSummaryDTO> findAllByNews() {
        List<Article> articles = articleRepository.findAllByCreationDateBetweenOrderByCreationDateDesc(Instant.now().minus(1, ChronoUnit.DAYS), Instant.now());
        List<ArticleSummaryDTO> res = new ArrayList<>();

        for(Article article: articles){
            res.add(ArticleSummaryDTO
                    .builder()
                    .num(article.getId())
                    .title(article.getTitle())
                    .username(article.getUser().getUsername())
                    .hit(article.getHit())
                    .creationDate(article.getCreationDate())
                    .build());
        }
        return res;
    }

    public List<ArticleSummaryDTO> findAllByRecentPosts() {
        List<Article> articles = articleRepository.findAllByOrderByCreationDateDesc();
        List<ArticleSummaryDTO> res = new ArrayList<>();

        for(Article article: articles){
            res.add(ArticleSummaryDTO
                    .builder()
                    .num(article.getId())
                    .title(article.getTitle())
                    .username(article.getUser().getUsername())
                    .hit(article.getHit())
                    .creationDate(article.getCreationDate())
                    .build());
        }
        return res;
    }

    public ArticleDetailDTO findArticleDetailDTOById(Long articleId) {
        Article article = articleRepository.findById(articleId).get();
        return ArticleDetailDTO.builder()
                .num(article.getId())
                .content(article.getContent())
                .title(article.getTitle())
                .username(article.getUser().getUsername())
                .hit(article.getHit())
                .creationDate(article.getCreationDate())
                .build();
    }
}
