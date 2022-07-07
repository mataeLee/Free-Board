package co.kr.promptech.freeboard.controller;

import co.kr.promptech.freeboard.dto.CommentDTO;
import co.kr.promptech.freeboard.model.Account;
import co.kr.promptech.freeboard.model.Article;
import co.kr.promptech.freeboard.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{articleId}")
    public String post(@PathVariable Long articleId, CommentDTO commentDTO, HttpSession httpSession, Principal principal) {
        Account account = (Account) httpSession.getAttribute("account_" + principal.getName());
        Article article = (Article) httpSession.getAttribute("article_" + commentDTO.getArticleId());
        commentService.save(commentDTO, account, article);
        return "redirect:/articles/"+ commentDTO.getArticleId();
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id){
        commentService.delete(id);
        return "redirect:/accounts/comments";
    }
}