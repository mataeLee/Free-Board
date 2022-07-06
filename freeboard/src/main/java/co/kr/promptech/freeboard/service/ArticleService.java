package co.kr.promptech.freeboard.service;

import co.kr.promptech.freeboard.dto.ArticleDetailDTO;
import co.kr.promptech.freeboard.dto.ArticleSummaryDTO;
import co.kr.promptech.freeboard.model.Account;
import co.kr.promptech.freeboard.model.Article;
import co.kr.promptech.freeboard.repository.ArticleRepository;
import co.kr.promptech.freeboard.util.InstantFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public void save(Article article){
        articleRepository.save(article);
    }

    public void save(ArticleDetailDTO articleDetail, Account account) {
        Article article = null;
        if(articleDetail.getId() != null) article = articleRepository.findById(articleDetail.getId()).orElse(null);
        if(Objects.isNull(article)) {
            article = Article.builder()
                    .content(articleDetail.getContent())
                    .title(articleDetail.getTitle())
                    .user(account)
                    .build();
        }
        else{
            article.setTitle(articleDetail.getTitle());
            article.setContent(articleDetail.getContent());
        }
        articleRepository.save(article);
    }

    public void addHit(Long articleId){
        articleRepository.addHitByArticleId(articleId);
    }

    public List<ArticleSummaryDTO> findAllByNews() {
        Instant before = Instant.now().minus(1, ChronoUnit.DAYS);
        Instant after = Instant.now();
        List<Article> articles = articleRepository.findAllByCreationDateBetweenOrderByCreationDateDesc(before, after);
        List<ArticleSummaryDTO> res = new ArrayList<>();

        for(Article article: articles){
            String creationDate = InstantFormatter.formatString(article.getCreationDate());
            String username = article.getUser().getUsername();

            res.add(ArticleSummaryDTO
                    .builder()
                    .id(article.getId())
                    .title(article.getTitle())
                    .username(username)
                    .hit(article.getHit())
                    .creationDate(creationDate)
                    .build());
        }
        return res;
    }

    public List<ArticleSummaryDTO> findAllByRecentPosts() {
        List<Article> articles = articleRepository.findAllByOrderByCreationDateDesc();
        List<ArticleSummaryDTO> res = new ArrayList<>();

        for(Article article: articles){
            String creationDate = InstantFormatter.formatString(article.getCreationDate());
            String username = article.getUser().getUsername();

            res.add(ArticleSummaryDTO
                    .builder()
                    .id(article.getId())
                    .title(article.getTitle())
                    .username(username)
                    .hit(article.getHit())
                    .creationDate(creationDate)
                    .build());
        }
        return res;
    }

    public ArticleDetailDTO findArticleDetailDTOById(Long articleId) {
        Article article = articleRepository.findById(articleId).orElse(null);

        if(Objects.isNull(article)) throw new NullPointerException("article not found");

        String creationDate = InstantFormatter.formatString(article.getCreationDate());
        String updateDate = InstantFormatter.formatString(article.getUpateDate());
        String username = article.getUser().getUsername();

        return ArticleDetailDTO.builder()
                .id(article.getId())
                .content(article.getContent())
                .title(article.getTitle())
                .username(username)
                .hit(article.getHit())
                .creationDate(creationDate)
                .updateDate(updateDate)
                .build();
    }

    public List<ArticleSummaryDTO> findAllByAccount(Account account){
        List<Article> articles = articleRepository.findAllByUserOrderByCreationDateDesc(account);
        List<ArticleSummaryDTO> res = new ArrayList<>();

        for(Article article: articles){
            String creationDate = InstantFormatter.formatString(article.getCreationDate());
            String username = article.getUser().getUsername();

            res.add(ArticleSummaryDTO
                    .builder()
                    .id(article.getId())
                    .title(article.getTitle())
                    .username(username)
                    .hit(article.getHit())
                    .creationDate(creationDate)
                    .build());
        }
        return res;
    }

    public void delete(Long id) {
        Article article = articleRepository.findById(id).orElse(null);
        if(Objects.isNull(article)) throw new NullPointerException("article not found");
        articleRepository.delete(article);
    }

    public Article findById(Long id) {
        Article article = articleRepository.findById(id).orElse(null);
        if(Objects.isNull(article)) throw new NullPointerException("article not found");
        return article;
    }
}
