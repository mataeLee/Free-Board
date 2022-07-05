package co.kr.promptech.freeboard.controller;

import co.kr.promptech.freeboard.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class PageController {
    private final AccountService accountService;

    @GetMapping("/")
    public String home(){
        return "/pages/index";
    }

    @GetMapping("/denied")
    public String denied(){
        return "error/401";
    }
}
