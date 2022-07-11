package co.kr.promptech.freeboard.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    private String profileImage;

    @Builder
    public Account(Long id, String username, String email, String password, String profileImage){
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.profileImage = profileImage;
    }
}