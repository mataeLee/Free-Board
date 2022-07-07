package co.kr.promptech.freeboard.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;

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

    @Column(length = 10000)
    private String content;

    @Column
    @ColumnDefault("0")
    private int hit;

    @CreationTimestamp
    private Instant creationDate;

    @UpdateTimestamp
    private Instant upateDate;

    @ManyToOne(targetEntity = Account.class, fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    @ColumnDefault("0")
    private Account user;

    @Builder
    public Article(Long id, String title, String content, int hit, Instant creationDate, Instant upateDate, Account user) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.hit = hit;
        this.creationDate = creationDate;
        this.upateDate = upateDate;
        this.user = user;
    }
}
