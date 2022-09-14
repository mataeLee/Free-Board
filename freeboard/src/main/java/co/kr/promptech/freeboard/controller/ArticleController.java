package co.kr.promptech.freeboard.controller;

import co.kr.promptech.freeboard.dto.ArticleDetailDTO;
import co.kr.promptech.freeboard.dto.ArticleSummaryDTO;
import co.kr.promptech.freeboard.dto.CommentDTO;
import co.kr.promptech.freeboard.model.Account;
import co.kr.promptech.freeboard.model.Article;
import co.kr.promptech.freeboard.service.ArticleService;
import co.kr.promptech.freeboard.util.ArticleFormatter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {
    private Logger logger = LoggerFactory.getLogger(ArticleController.class);
    private final ArticleService articleService;

    @GetMapping("")
    public String index() {
        return "app/home/index";
    }

    @GetMapping("/scroll")
    @ResponseBody
    public Map<String, Object> scroll(@PageableDefault(size = 9, page = 0, sort = "creationDate", direction = Sort.Direction.DESC) Pageable pageable){
        Slice<Article> entities = articleService.findSliceBy(pageable);
        logger.info("content size : " + entities.getContent().size());

        List<ArticleSummaryDTO> articles = new ArrayList<>();

        for(Article article: entities){
            articles.add(ArticleFormatter.toSummaryDTO(article));
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("articles", articles);
        return resultMap;
    }

    @PostMapping()
    public String post(@ModelAttribute("articleDetail") @Validated ArticleDetailDTO articleDetailDTO, BindingResult result, HttpSession httpSession, Principal principal) {
        if(result.hasErrors()){
            return "app/articles/edit";
        }
        Account account = (Account) httpSession.getAttribute("account_" + principal.getName());

        articleService.save(articleDetailDTO, account);
        return "redirect:/articles";
    }

    @GetMapping("/new")
    public String postForm(Model model) {
        model.addAttribute("articleDetail", new ArticleDetailDTO());
        return "app/articles/new";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        ArticleDetailDTO articleDetailDTO = articleService.findArticleDetailDTOById(id);
        model.addAttribute("articleDetail", articleDetailDTO);
        model.addAttribute("comment", new CommentDTO());
        return "app/articles/show";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        articleService.delete(id);
        return "redirect:/articles";
    }

    @GetMapping("/{id}/edit")
    public String updateForm(@PathVariable Long id, Model model) {
        ArticleDetailDTO articleDetailDTO = articleService.findArticleDetailDTOById(id);
        model.addAttribute("articleDetail", articleDetailDTO);
        return "app/articles/edit";
    }

    @PutMapping("/{id}")
    public String update( @PathVariable Long id, ArticleDetailDTO articleDetail, HttpSession httpSession, Principal principal) {
        Account account = (Account) httpSession.getAttribute("account_" + principal.getName());
        articleService.save(articleDetail, account);
        return "redirect:/articles";
    }
}