package co.kr.promptech.freeboard.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class ArticleSummaryDTO {
    private Long num;
    private String title;
    private String username;
    private int hit;
    private String creationDate;

    @Builder
    public ArticleSummaryDTO(Long num, String title, String username, int hit, String creationDate){
        this.num = num;
        this.title = title;
        this.username = username;
        this.hit = hit;
        this.creationDate = creationDate;
    }
}
