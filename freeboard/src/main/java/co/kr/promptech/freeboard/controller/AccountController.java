package co.kr.promptech.freeboard.controller;

import co.kr.promptech.freeboard.dto.AccountDTO;
import co.kr.promptech.freeboard.dto.ArticleSummaryDTO;
import co.kr.promptech.freeboard.dto.CommentDTO;
import co.kr.promptech.freeboard.model.Account;
import co.kr.promptech.freeboard.service.AccountService;
import co.kr.promptech.freeboard.service.ArticleService;
import co.kr.promptech.freeboard.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    public String show(Model model, HttpSession httpSession, Principal principal){
        try {
            Account account = (Account) httpSession.getAttribute("account_" + principal.getName());
            model.addAttribute("account", account);
        }catch (Exception e){
            e.printStackTrace();
            return "pages/index";
        }
        return "pages/accounts/show";
    }

    @GetMapping("/accounts/articles")
    public String showArticles(Model model, HttpSession httpSession, Principal principal, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size){
        try {
            Account account = (Account) httpSession.getAttribute("account_" + principal.getName());
            int currentPage = page.orElse(1);
            int pageSize = size.orElse(5);

            Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
            Page<ArticleSummaryDTO> articlePage = articleService.findAllByAccountByPaginated(account, pageable);
            model.addAttribute("articles", articlePage);
            int totalPages = articlePage.getTotalPages();
            if (totalPages > 0) {
                List<Integer> pageNums = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
                model.addAttribute("pageNums", pageNums);
            }
        }catch (Exception e){
            e.printStackTrace();
            return "pages/index";
        }
        return "pages/accounts/articles";
    }

    @GetMapping("/accounts/comments")
    public String showComments(Model model, HttpSession httpSession, Principal principal, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size){
        try {
            Account account = (Account) httpSession.getAttribute("account_" + principal.getName());
            int currentPage = page.orElse(1);
            int pageSize = size.orElse(5);

            Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
            Page<CommentDTO> commentPage = commentService.findAllByAccountByPaginated(account, pageable);
            model.addAttribute("comments", commentPage);
            int totalPages = commentPage.getTotalPages();
            if (totalPages > 0) {
                List<Integer> pageNums = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
                model.addAttribute("pageNums", pageNums);
            }
        }catch (Exception e){
            e.printStackTrace();
            return "pages/index";
        }
        return "pages/accounts/comments";
    }

    @PutMapping("/accounts")
    public String update(@PathVariable Long id, AccountDTO accountDTO, HttpSession session, Principal principal){
        try {
            Account account = (Account) session.getAttribute("account_" + principal.getName());


        }catch (Exception e){
            e.printStackTrace();
            return "pages/index";
        }

        return "redirect:/accounts";
    }

    @PutMapping("/accounts/profileImage")
    public String updateProfile(MultipartFile profile, HttpSession session, Principal principal){
        try {
            Account account = (Account) session.getAttribute("account_" + principal.getName());
            accountService.updateProfileImage(account, profile);
        }catch (Exception e){
            e.printStackTrace();
            return "pages/index";
        }
        return "redirect:/accounts";
    }
}