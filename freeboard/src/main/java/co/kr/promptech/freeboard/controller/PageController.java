package co.kr.promptech.freeboard.controller;

import co.kr.promptech.freeboard.dto.AccountDTO;
import co.kr.promptech.freeboard.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PageController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/")
    public String base(){
        return "redirect:articles/today";
    }

    @GetMapping("/home")
    public String home(){
        return "redirect:articles/today";
    }

    @GetMapping("/bootstrap")
    public String index(){
        return "pages/bootstrap";
    }

    @GetMapping("/login")
    public String login(){
        return "pages/account/login";
    }

    @GetMapping("/signup")
    public String signup(){
        return "pages/account/new";
    }

    @GetMapping("/denied")
    public String denied(){
        return "error/401";
    }

    @GetMapping("/user/page")
    public String myPage(Model model){
//        model.addAttribute("user_id", model.getAttribute());
        return "pages/account/show";
    }

    @PostMapping("/signup")
    public String signupProcess(AccountDTO accountDTO){
        accountService.save(accountDTO);
        return "redirect:/login";
    }
}
