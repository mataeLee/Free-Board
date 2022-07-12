package co.kr.promptech.freeboard.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
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

    /**
     *  column default = 0 오류로 인한 account id가 null인경우 admin 계정의 id값으로 저장
     */
    @ManyToOne(targetEntity = Account.class, fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
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
