package co.kr.promptech.freeboard.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class ArticleDetailDTO {
    private Long num;
    private String title;
    private String content;
    private String username;
    private int hit;
    private String creationDate;

    @Builder
    public ArticleDetailDTO(Long num, String title, String username, int hit, String creationDate, String content){
        this.num = num;
        this.title = title;
        this.content = content;
        this.username = username;
        this.hit = hit;
        this.creationDate = creationDate;
    }
}
