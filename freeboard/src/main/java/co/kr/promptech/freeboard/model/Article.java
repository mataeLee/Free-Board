package co.kr.promptech.freeboard.model;

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

    @ManyToOne(targetEntity = Account.class, fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private Account user;
}
