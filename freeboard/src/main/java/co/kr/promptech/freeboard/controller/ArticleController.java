package co.kr.promptech.freeboard.controller;

import co.kr.promptech.freeboard.dto.ArticleDetailDTO;
import co.kr.promptech.freeboard.dto.ArticleSummaryDTO;
import co.kr.promptech.freeboard.dto.CommentDTO;
import co.kr.promptech.freeboard.service.ArticleService;
import co.kr.promptech.freeboard.service.CommentService;
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

    private final CommentService commentService;

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
    public String post(ArticleDetailDTO articleDetailDTO, Principal principal){
        articleDetailDTO.setUsername(principal.getName());
        articleService.save(articleDetailDTO);
        return "redirect:/articles/news";
    }

    @GetMapping("/new")
    public String postForm(Model model){
        model.addAttribute("articleDetail", new ArticleDetailDTO());
        return "pages/articles/new";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model){
        ArticleDetailDTO articleDetailDTO = articleService.findArticleDetailDTOById(id);
        articleService.addHit(id);
        List<CommentDTO> comments = commentService.findByArticle(articleDetailDTO.getId());
        articleDetailDTO.setComments(comments);
        model.addAttribute("articleDetail", articleDetailDTO);
        model.addAttribute("comment", new CommentDTO());
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
    public String update(ArticleDetailDTO articleDetail){
        articleService.save(articleDetail);
        return "redirect:/accounts";
    }
}