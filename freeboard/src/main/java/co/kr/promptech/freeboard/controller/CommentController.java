package co.kr.promptech.freeboard.controller;

import co.kr.promptech.freeboard.dto.ArticleDetailDTO;
import co.kr.promptech.freeboard.dto.CommentDTO;
import co.kr.promptech.freeboard.model.Account;
import co.kr.promptech.freeboard.model.Article;
import co.kr.promptech.freeboard.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{articleId}")
    public String post(CommentDTO commentDTO, HttpSession httpSession, Principal principal) {
        Account account = (Account) httpSession.getAttribute("account_" + principal.getName());
        Article article = (Article) httpSession.getAttribute("article_" + commentDTO.getArticleId());
        commentService.save(commentDTO, account, article);
        return "redirect:/articles/"+ commentDTO.getArticleId();
    }

    @DeleteMapping("/{id}/{articleId}")
    public String delete(@PathVariable Map<String, String> pathVarsMap){
        String targetUrl = "/accounts/comments";
        if(pathVarsMap.containsKey("articleId") && Long.parseLong(pathVarsMap.get("articleId")) > 0){
            targetUrl = "/articles/" + pathVarsMap.get("articleId");
        }
        commentService.delete(Long.parseLong(pathVarsMap.get("id")));
        return "redirect:" + targetUrl;
    }
}