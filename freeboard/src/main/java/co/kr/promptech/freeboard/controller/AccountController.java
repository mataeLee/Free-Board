package co.kr.promptech.freeboard.controller;

import co.kr.promptech.freeboard.dto.AccountDTO;
import co.kr.promptech.freeboard.dto.ArticleSummaryDTO;
import co.kr.promptech.freeboard.dto.CommentDTO;
import co.kr.promptech.freeboard.model.Account;
import co.kr.promptech.freeboard.model.Article;
import co.kr.promptech.freeboard.service.AccountService;
import co.kr.promptech.freeboard.service.ArticleService;
import co.kr.promptech.freeboard.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    private final ArticleService articleService;

    private final CommentService commentService;

    @GetMapping("/login")
    public String login(HttpSession session){
        return "pages/accounts/login";
    }

    @GetMapping("/login/success")
    public String loginSuccess(HttpSession session, Principal principal){
        Account account = accountService.findAccountByUsername(principal.getName());
        session.setAttribute("account", account);
        return "redirect:/";
    }

    @GetMapping("/signup")
    public String signupForm(Model model){
        model.addAttribute("accountDTO", new AccountDTO());
        return "pages/accounts/new";
    }

    @PostMapping("/signup")
    public String post(@Validated AccountDTO accountDTO, BindingResult result){
        Account account = accountService.findAccountByUsername(accountDTO.getUsername());
        if(account != null){
            result.rejectValue("username", "error.username", "Username already in use");
        }
        if(result.hasErrors()){
            return "pages/accounts/new";
        }
        accountService.save(accountDTO);
        return "redirect:/login";
    }

    @GetMapping("/accounts")
    public String show(Model model, HttpSession httpSession){
        Account account = (Account) httpSession.getAttribute("account");
        List<ArticleSummaryDTO> articles = articleService.findAllByAccount(account);
        List<CommentDTO> comments = commentService.findByAccount(account);
        model.addAttribute("articles", articles);
        model.addAttribute("comments", comments);
        return "pages/accounts/show";
    }

    @GetMapping("/accounts/articles")
    public String showArticles(Model model, HttpSession httpSession){
        Account account = (Account) httpSession.getAttribute("account");
        List<ArticleSummaryDTO> articles = articleService.findAllByAccount(account);
        model.addAttribute("articles", articles);
        return "pages/accounts/articles";
    }

    @GetMapping("/accounts/comments")
    public String showComments(Model model, HttpSession httpSession){
        Account account = (Account) httpSession.getAttribute("account");
        List<CommentDTO> comments = commentService.findByAccount(account);
        model.addAttribute("comments", comments);
        return "pages/accounts/comments";
    }
}