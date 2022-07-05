package co.kr.promptech.freeboard.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class CommentDTO {
    private Long id;

    private String content;

    private String creationDate;

    private String username;

    private Long articleId;

    @Builder
    public CommentDTO(Long id, String content, String creationDate, String username, Long articleId) {
        this.id = id;
        this.content = content;
        this.creationDate = creationDate;
        this.username = username;
        this.articleId = articleId;
    }
}
