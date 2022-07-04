package co.kr.promptech.freeboard.controller;

import co.kr.promptech.freeboard.dto.AccountDTO;
import co.kr.promptech.freeboard.dto.ArticleSummaryDTO;
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

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    private final ArticleService articleService;

    private final CommentService commentService;

    @GetMapping("/login")
    public String login(){
        return "pages/accounts/login";
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
    public String show(Model model, Principal principal){
        Account account = accountService.findAccountByUsername(principal.getName());
        List<ArticleSummaryDTO> articles = articleService.findAllByUser(account);

        model.addAttribute("articles", articles);
        return "pages/accounts/show";
    }
}
