package co.kr.promptech.freeboard.controller;

import co.kr.promptech.freeboard.dto.AccountDTO;
import co.kr.promptech.freeboard.model.Account;
import co.kr.promptech.freeboard.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/login")
    public String login(){
        return "pages/accounts/login";
    }

    @GetMapping("/signup")
    public String signupForm(){
        return "pages/accounts/new";
    }

    @PostMapping("/signup")
    public String post(AccountDTO accountDTO){
        accountService.save(accountDTO);
        return "redirect:/login";
    }

    @GetMapping("/accounts")
    public String show(Model model, Authentication authentication){
        String username = authentication.getName();


        return "pages/accounts/show";
    }
}
