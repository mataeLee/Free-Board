package co.kr.promptech.freeboard.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 100)
    private String content;

    @CreationTimestamp
    private Instant creationDate;

    @ManyToOne(targetEntity = Account.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ColumnDefault("0")
    private Account user;

    @ManyToOne()
    @JoinColumn(name = "article_id")
    private Article article;

    @Builder
    public Comment(Long id, String content, Instant creationDate, Account user, Article article) {
        this.id = id;
        this.content = content;
        this.creationDate = creationDate;
        this.user = user;
        this.article = article;
    }
}