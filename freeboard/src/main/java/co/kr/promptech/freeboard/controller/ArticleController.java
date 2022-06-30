package co.kr.promptech.freeboard.controller;

import co.kr.promptech.freeboard.dto.ArticleDetailDTO;
import co.kr.promptech.freeboard.dto.ArticleSummaryDTO;
import co.kr.promptech.freeboard.model.Article;
import co.kr.promptech.freeboard.service.ArticleService;
import co.kr.promptech.freeboard.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/articles/new")
    public String postForm(){
        return "pages/article/new";
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable("id") Long id, Model model){
        ArticleDetailDTO articleDetailDTO = articleService.findArticleDetailDTOById(id);
        articleService.addHit(id);
        model.addAttribute("articleDetail", articleDetailDTO);
        return "pages/article/show";
    }

    @GetMapping("/articles/today")
    public String todayIndex(Model model){
        List<ArticleSummaryDTO> articles = articleService.findTodays();
        model.addAttribute("todayArticles", articles);
        return "pages/article/index";
    }

    @GetMapping("/articles")
    public String index(Model model){
        List<ArticleSummaryDTO> articles = articleService.findAllArticles();
        model.addAttribute("todayArticles", articles);
        return "pages/article/index";
    }

    @PostMapping("/articles")
    //TODO VO 객체 사용
    public String post(String subject, String content, Principal principal){
        Article article = new Article();
        article.setUser(accountService.findAccountByUsername(principal.getName()));
        article.setContent(content);
        article.setTitle(subject);
        articleService.save(article);
        return "redirect:/articles";
    }
}