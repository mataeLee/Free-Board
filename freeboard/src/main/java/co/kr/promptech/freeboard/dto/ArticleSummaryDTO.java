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
    private Long id;
    private int num;
    private String title;
    private String summary;
    private String username;
    private String thumbnail;
    private int hit;
    private String creationDate;

    @Builder
    public ArticleSummaryDTO(Long id, int num, String title, String summary, String username, String thumbnail, int hit, String creationDate) {
        this.id = id;
        this.num = num;
        this.title = title;
        this.summary = summary;
        this.username = username;
        this.thumbnail = thumbnail;
        this.hit = hit;
        this.creationDate = creationDate;
    }
}
