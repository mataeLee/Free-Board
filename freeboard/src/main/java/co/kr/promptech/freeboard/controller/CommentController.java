package co.kr.promptech.freeboard.controller;

import co.kr.promptech.freeboard.dto.CommentDTO;
import co.kr.promptech.freeboard.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{articleId}")
    public String post(@PathVariable Long articleId, CommentDTO commentDTO, Principal principal) {
        commentDTO.setUsername(principal.getName());
        commentDTO.setArticleId(articleId);
        commentService.save(commentDTO);
        return "redirect:/articles/"+ commentDTO.getArticleId();
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id){
        commentService.delete(id);
        return "redirect:/accounts/comments";
    }
}