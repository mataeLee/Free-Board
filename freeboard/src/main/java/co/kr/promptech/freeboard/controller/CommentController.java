package co.kr.promptech.freeboard.controller;

import co.kr.promptech.freeboard.dto.CommentDTO;
import co.kr.promptech.freeboard.model.Account;
import co.kr.promptech.freeboard.model.Article;
import co.kr.promptech.freeboard.model.Comment;
import co.kr.promptech.freeboard.service.ArticleService;
import co.kr.promptech.freeboard.service.CommentService;
import co.kr.promptech.freeboard.util.CommentFormatter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private Logger logger = LoggerFactory.getLogger(CommentController.class);
    private final CommentService commentService;

    private final ArticleService articleService;

    @GetMapping("/{articleId}")
    @ResponseBody
    public Map<String, Object> index(@PathVariable Long articleId, @PageableDefault(size = 10, page = 0, sort = "creationDate", direction = Sort.Direction.DESC) Pageable pageable){
        Page<Comment> entities = commentService.findPageByArticleId(articleId, pageable);
        logger.info("content size : " + entities.getContent().size());

        List<CommentDTO> comments = new ArrayList<>();

        for(Comment comment: entities){
            comments.add(CommentFormatter.toDTO(comment));
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("comments", comments);
        return resultMap;
    }

    @PostMapping("/{articleId}")
    @ResponseBody
    public Map<String, Object> post(@PathVariable Long articleId, CommentDTO commentDTO, HttpSession httpSession, Principal principal) {
        Account account = (Account) httpSession.getAttribute("account_" + principal.getName());
        Article article = articleService.findById(articleId);

        Comment comment = CommentFormatter.toEntity(commentDTO, account, article);
        Comment obj = commentService.save(comment);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("comment", CommentFormatter.toDTO(obj));
        return resultMap;
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