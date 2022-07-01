package co.kr.promptech.freeboard.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class AccountDTO {
    @NotBlank(message = "Username required")
    @Size(min = 5, max = 15)
    private String username;
    @NotBlank(message = "Password required")
    @Size(min = 3, max = 15)
    private String password;
    @NotBlank(message = "Email required")
    @Email()
    private String email;
    @Builder
    public AccountDTO(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
