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
    private String title;
    private String username;
    private int hit;
    private String creationDate;

    @Builder
    public ArticleSummaryDTO(Long id, String title, String username, int hit, String creationDate){
        this.id = id;
        this.title = title;
        this.username = username;
        this.hit = hit;
        this.creationDate = creationDate;
    }
}
