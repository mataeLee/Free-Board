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

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    private final AccountService accountService;

    public void save(Article article){
        articleRepository.save(article);
    }

    public void save(ArticleDetailDTO articleDetail) {
        Article article = null;
        if(articleDetail.getId() != null) article = articleRepository.findById(articleDetail.getId()).orElse(null);
        if(article == null) {
            Account user = accountService.findAccountByUsername(articleDetail.getUsername());
            article = Article.builder()
                    .content(articleDetail.getContent())
                    .title(articleDetail.getTitle())
                    .user(user)
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
        List<Article> articles = articleRepository.findAllByCreationDateBetweenOrderByCreationDateDesc(Instant.now().minus(1, ChronoUnit.DAYS), Instant.now());
        List<ArticleSummaryDTO> res = new ArrayList<>();

        for(Article article: articles){
            res.add(ArticleSummaryDTO
                    .builder()
                    .id(article.getId())
                    .title(article.getTitle())
                    .username(article.getUser().getUsername())
                    .hit(article.getHit())
                    .creationDate(InstantFormatter.formatString(article.getCreationDate()))
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
                    .id(article.getId())
                    .title(article.getTitle())
                    .username(article.getUser().getUsername())
                    .hit(article.getHit())
                    .creationDate(InstantFormatter.formatString(article.getCreationDate()))
                    .build());
        }
        return res;
    }

    public ArticleDetailDTO findArticleDetailDTOById(Long articleId) {
        Article article = articleRepository.findById(articleId).orElse(null);
        return ArticleDetailDTO.builder()
                .id(article.getId())
                .content(article.getContent())
                .title(article.getTitle())
                .username(article.getUser().getUsername())
                .hit(article.getHit())
                .creationDate(InstantFormatter.formatString(article.getCreationDate()))
                .updateDate(InstantFormatter.formatString(article.getUpateDate()))
                .build();
    }

    public List<ArticleSummaryDTO> findAllByAccount(Account user){
        List<Article> articles = articleRepository.findAllByUser(user);
        List<ArticleSummaryDTO> res = new ArrayList<>();

        for(Article article: articles){
            res.add(ArticleSummaryDTO
                    .builder()
                    .id(article.getId())
                    .title(article.getTitle())
                    .username(article.getUser().getUsername())
                    .hit(article.getHit())
                    .creationDate(InstantFormatter.formatString(article.getCreationDate()))
                    .build());
        }
        return res;
    }

    public void delete(Long id) {
        Article article = articleRepository.findById(id).orElse(null);
        articleRepository.delete(article);
    }

    public Article findById(Long id) {
        return articleRepository.findById(id).orElse(null);
    }
}
