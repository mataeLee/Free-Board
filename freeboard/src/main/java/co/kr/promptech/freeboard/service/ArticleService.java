package co.kr.promptech.freeboard.service;

import co.kr.promptech.freeboard.dto.ArticleDetailDTO;
import co.kr.promptech.freeboard.dto.ArticleSummaryDTO;
import co.kr.promptech.freeboard.model.Article;
import co.kr.promptech.freeboard.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    public void save(Article article){
        articleRepository.save(article);
    }

    public void addHit(Long articleId){
        articleRepository.addHitByArticleId(articleId);
    }

    public List<ArticleSummaryDTO> findTodays() {
        List<Article> articles = articleRepository.findAllByCreatedToday();
        List<ArticleSummaryDTO> res = new ArrayList<>();

        for(int i=0; i<articles.size(); i++){
            Article article = articles.get(articles.size()-1-i);
            res.add(ArticleSummaryDTO
                    .builder()
                    .num(article.getId())
                    .title(article.getTitle())
                    .username(article.getUser().getUsername())
                    .hit(article.getHit())
                    .build());
        }
        return res;
    }

    public List<ArticleSummaryDTO> findAllArticles() {
        Iterator<Article> iterator = articleRepository.findAll().iterator();
        List<ArticleSummaryDTO> res = new ArrayList<>();

        while (iterator.hasNext()){
            Article article = iterator.next();
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
