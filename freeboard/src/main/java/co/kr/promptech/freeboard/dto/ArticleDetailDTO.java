package co.kr.promptech.freeboard.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ArticleDetailDTO {
    private Long id;
    private String title;
    private String summary;
    private String content;
    private String username;
    private String userprofile;
    private int hit;
    private String creationDate;
    private String updateDate;

    private List<CommentDTO> comments;

    @Builder
    public ArticleDetailDTO(Long id, String title, String summary, String content, String username, String userprofile, int hit, String creationDate, String updateDate, List<CommentDTO> comments) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.content = content;
        this.username = username;
        this.userprofile = userprofile;
        this.hit = hit;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
        this.comments = comments;
    }
}
