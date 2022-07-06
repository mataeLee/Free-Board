package co.kr.promptech.freeboard.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PageController {
    @GetMapping("/")
    public String home(){
        return "redirect:/articles/news";
    }

    @GetMapping("/denied")
    public String denied(){
        return "error/401";
    }
}
