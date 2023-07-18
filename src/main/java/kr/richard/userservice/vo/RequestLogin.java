package kr.richard.userservice.vo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RequestLogin {

    @NotNull(message = "이메일 주소를 정확히 입력해주세요.")
    @Size(min = 2, message = "최소 2자 이상 입력해주세요")
    @Email
    private String email;

    @NotNull(message = "비밀번호를 정확히 입력해주세요.")
    @Size(min = 8, message = "최소 8자 이상 입력해줏세요")
    private String password;

}
