package co.kr.promptech.freeboard.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Getter
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 100)
    private String content;

    @UpdateTimestamp
    private Instant uptDate;

    @ManyToOne(targetEntity = Account.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Account user;

    @ManyToOne(targetEntity = Article.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    @Builder
    public Comment(Long id, String content, Instant uptDate, Account user, Article article) {
        this.id = id;
        this.content = content;
        this.uptDate = uptDate;
        this.user = user;
        this.article = article;
    }
}
