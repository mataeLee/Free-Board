package co.kr.promptech.freeboard.service;

import co.kr.promptech.freeboard.dto.ArticleDetailDTO;
import co.kr.promptech.freeboard.dto.ArticleSummaryDTO;
import co.kr.promptech.freeboard.model.Account;
import co.kr.promptech.freeboard.model.Article;
import co.kr.promptech.freeboard.repository.ArticleRepository;
import co.kr.promptech.freeboard.util.ArticleFormatter;
import co.kr.promptech.freeboard.util.InstantFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public void save(Article article) {
        articleRepository.save(article);
    }

    @Transactional
    public void save(ArticleDetailDTO articleDetail, Account account) {
        Article article = null;
        if (articleDetail.getId() != null) article = articleRepository.findById(articleDetail.getId()).orElse(null);
        if (Objects.isNull(article)) {
            article = Article.builder()
                    .content(articleDetail.getContent())
                    .title(articleDetail.getTitle())
                    .user(account)
                    .build();
        } else {
            article.setTitle(articleDetail.getTitle());
            article.setContent(articleDetail.getContent());
        }
        articleRepository.save(article);
    }

    public void addHit(Long articleId) {
        articleRepository.addHitByArticleId(articleId);
    }

    public Page<ArticleSummaryDTO> findNewsByPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        Instant before = Instant.now().minus(1, ChronoUnit.DAYS);
        Instant after = Instant.now();
        List<Article> articles = articleRepository.findAllByCreationDateBetweenOrderByHitDescCreationDateDesc(before, after);
        List<Article> list;

        if(articles.size() < startItem){
            list = articles;
        }
        else{
            int toIndex = Math.min(startItem + pageSize, articles.size());
            list = articles.subList(startItem, toIndex);
        }

        List<ArticleSummaryDTO> res = new ArrayList<>();
        for (int i=0; i<list.size(); i++){
            ArticleSummaryDTO dto = ArticleFormatter.toSummaryDTO(list.get(i));
            dto.setNum(i+1);
            res.add(dto);
        }

        Page<ArticleSummaryDTO> articlePage = new PageImpl<>(res, PageRequest.of(currentPage, pageSize), articles.size());
        return articlePage;
    }

    public Page<ArticleSummaryDTO> findAllByPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Article> articles = articleRepository.findAllByOrderByCreationDateDesc();
        List<Article> list;

        if(articles.size() < startItem){
            list = articles;
        }
        else{
            int toIndex = Math.min(startItem + pageSize, articles.size());
            list = articles.subList(startItem, toIndex);

        }

        List<ArticleSummaryDTO> res = new ArrayList<>();
        for (int i=0; i<list.size(); i++){
            ArticleSummaryDTO dto = ArticleFormatter.toSummaryDTO(list.get(i));
            dto.setNum(i+1);
            res.add(dto);
        }

        Page<ArticleSummaryDTO> articlePage = new PageImpl<>(res, PageRequest.of(currentPage, pageSize), articles.size());
        return articlePage;
    }

    public Page<ArticleSummaryDTO> findAllByAccountByPaginated(Account account, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Article> articles = articleRepository.findAllByUserOrderByCreationDateDesc(account);
        List<Article> list;

        if(articles.size() < startItem){
            list = articles;
        }
        else{
            int toIndex = Math.min(startItem + pageSize, articles.size());
            list = articles.subList(startItem, toIndex);

        }

        List<ArticleSummaryDTO> res = new ArrayList<>();
        for (int i=0; i<list.size(); i++){
            ArticleSummaryDTO dto = ArticleFormatter.toSummaryDTO(list.get(i));
            dto.setNum(i+1);
            res.add(dto);
        }

        Page<ArticleSummaryDTO> articlePage = new PageImpl<>(res, PageRequest.of(currentPage, pageSize), articles.size());
        return articlePage;
    }

    public ArticleDetailDTO findArticleDetailDTOById(Long articleId) {
        Article article = articleRepository.findById(articleId).orElse(null);
        if (Objects.isNull(article)) throw new NullPointerException("article not found");

        return ArticleFormatter.toDetailDTO(article);
    }

    public void delete(Long id) {
        Article article = articleRepository.findById(id).orElse(null);
        if (Objects.isNull(article)) throw new NullPointerException("article not found");

        articleRepository.delete(article);
    }

    public Article findById(Long id) {
        Article article = articleRepository.findById(id).orElse(null);
        if (Objects.isNull(article)) throw new NullPointerException("article not found");
        return article;
    }

    public List<ArticleSummaryDTO> findAllByNews() {
        Instant before = Instant.now().minus(1, ChronoUnit.DAYS);
        Instant after = Instant.now();

        List<Article> articles = articleRepository.findAllByCreationDateBetweenOrderByHitDescCreationDateDesc(before, after);
        List<ArticleSummaryDTO> res = new ArrayList<>();
        for (int i=0; i<articles.size(); i++){
            ArticleSummaryDTO dto = ArticleFormatter.toSummaryDTO(articles.get(i));
            dto.setNum(i);
            res.add(dto);
        }
        return res;
    }

    public Slice<Article> findAll(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }
}