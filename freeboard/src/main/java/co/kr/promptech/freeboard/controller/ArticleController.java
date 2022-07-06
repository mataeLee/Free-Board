package co.kr.promptech.freeboard.controller;

import co.kr.promptech.freeboard.dto.ArticleDetailDTO;
import co.kr.promptech.freeboard.dto.ArticleSummaryDTO;
import co.kr.promptech.freeboard.dto.CommentDTO;
import co.kr.promptech.freeboard.model.Account;
import co.kr.promptech.freeboard.model.Article;
import co.kr.promptech.freeboard.service.ArticleService;
import co.kr.promptech.freeboard.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    private final CommentService commentService;

    @GetMapping()
    public String index(Model model){
        List<ArticleSummaryDTO> articles = articleService.findAllByRecentPosts();
        model.addAttribute("articles", articles);
        model.addAttribute("tableTitle", "Articles");
        return "pages/articles/index";
    }

    @GetMapping("/news")
    public String indexNews(Model model){
        List<ArticleSummaryDTO> articles = articleService.findAllByNews();
        model.addAttribute("articles", articles);
        model.addAttribute("tableTitle", "Articles News");
        return "pages/articles/index";
    }

    @PostMapping()
    public String post(ArticleDetailDTO articleDetailDTO, HttpSession httpSession){
        Account account = (Account) httpSession.getAttribute("account");
        articleService.save(articleDetailDTO, account);
        return "redirect:/articles/news";
    }

    @GetMapping("/new")
    public String postForm(Model model){
        model.addAttribute("articleDetail", new ArticleDetailDTO());
        return "pages/articles/new";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model, HttpSession httpSession){
        Article article = articleService.findById(id);
        //TODO spring cache 이용하여 조회수 조절
        articleService.addHit(id);
        List<CommentDTO> comments = commentService.findByArticle(article);

        ArticleDetailDTO articleDetailDTO = articleService.findArticleDetailDTOById(id);
        articleDetailDTO.setComments(comments);
        model.addAttribute("articleDetail", articleDetailDTO);
        model.addAttribute("comment", new CommentDTO());

        httpSession.setAttribute("article", article);
        return "pages/articles/show";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id){
        articleService.delete(id);
        return "redirect:/accounts/articles";
    }

    @GetMapping("/put/{id}")
    public String updateForm(@PathVariable Long id, Model model){
        ArticleDetailDTO articleDetailDTO = articleService.findArticleDetailDTOById(id);
        model.addAttribute("articleDetail", articleDetailDTO);
        return "pages/articles/put";
    }

    @PutMapping()
    public String update(ArticleDetailDTO articleDetail, HttpSession httpSession){
        Account account = (Account) httpSession.getAttribute("account");
        articleService.save(articleDetail, account);
        return "redirect:/accounts";
    }
}