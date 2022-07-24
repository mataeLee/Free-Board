package co.kr.promptech.freeboard.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import java.time.Instant;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Article {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String title;

    @Column(length = 5000)
    private String content;

    @Column(length = 150)
    private String summary;

    @Column
    private String thumbnail;

    @Column
    @ColumnDefault("0")
    private int hit;

    @CreationTimestamp
    private Instant creationDate;

    @UpdateTimestamp
    private Instant upateDate;

    @ManyToOne(targetEntity = Account.class, fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Account user;

    @OneToMany(mappedBy = "article", targetEntity = Comment.class, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Comment> comments;

    @Builder
    public Article(Long id, String title, String content, String summary, String thumbnail, int hit, Instant creationDate, Instant upateDate, Account user) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.summary = summary;
        this.thumbnail = thumbnail;
        this.hit = hit;
        this.creationDate = creationDate;
        this.upateDate = upateDate;
        this.user = user;
    }
}
