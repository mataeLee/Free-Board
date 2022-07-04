package co.kr.promptech.freeboard.controller;

import co.kr.promptech.freeboard.dto.ArticleDetailDTO;
import co.kr.promptech.freeboard.dto.ArticleSummaryDTO;
import co.kr.promptech.freeboard.model.Article;
import co.kr.promptech.freeboard.service.ArticleService;
import co.kr.promptech.freeboard.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;
    private final AccountService accountService;

    @GetMapping()
    public String index(Model model){
        List<ArticleSummaryDTO> articles = articleService.findAllByRecentPosts();
        model.addAttribute("articles", articles);
        model.addAttribute("tableTitle", "Articles");
        return "pages/index";
    }

    @GetMapping("/news")
    public String indexNews(Model model){
        List<ArticleSummaryDTO> articles = articleService.findAllByNews();
        model.addAttribute("articles", articles);
        model.addAttribute("tableTitle", "Articles News");
        return "pages/index";
    }

    @PostMapping()
    //TODO VO 객체 사용
    public String post(String subject, String content, Principal principal){
        Article article = new Article();
        article.setUser(accountService.findAccountByUsername(principal.getName()));
        article.setContent(content);
        article.setTitle(subject);
        articleService.save(article);
        return "redirect:/articles/news";
    }

    @GetMapping("/new")
    public String postForm(){
        return "pages/articles/new";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model){
        ArticleDetailDTO articleDetailDTO = articleService.findArticleDetailDTOById(id);
        articleService.addHit(id);
        model.addAttribute("articleDetail", articleDetailDTO);
        return "pages/articles/show";
    }
}