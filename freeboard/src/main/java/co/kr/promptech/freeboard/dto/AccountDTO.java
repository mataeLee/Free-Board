package co.kr.promptech.freeboard.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDTO {
    private String username;
    private String password;
    private String email;
    @Builder
    public AccountDTO(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
