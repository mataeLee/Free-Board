package co.kr.promptech.freeboard.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String content;
    private String username;
    private int hit;
    private String creationDate;
    private String updateDate;

    private List<CommentDTO> comments;

    @Builder
    public ArticleDetailDTO(Long id, String title, String content, String username, int hit, String creationDate, String updateDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.username = username;
        this.hit = hit;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
    }
}
