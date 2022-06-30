package co.kr.promptech.freeboard.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ArticleDetailDTO {
    private Long num;
    private String title;
    private String content;
    private String username;
    private int hit;
    private Instant creationDate;

    @Builder
    public ArticleDetailDTO(Long num, String title, String username, int hit, Instant creationDate, String content){
        this.num = num;
        this.title = title;
        this.content = content;
        this.username = username;
        this.hit = hit;
        this.creationDate = creationDate;
    }
}
