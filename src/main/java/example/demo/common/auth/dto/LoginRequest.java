package example.demo.common.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class LoginRequest {
    @NotBlank(message = "이메일 입력은 필수입니다.")
    @Size(max = 30, message = "30자 이하로 입력해주세요.")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "유효한 이메일 형식이 아닙니다.")
    public String email;

    @NotBlank(message = "암호 입력은 필수입니다.")
    @Size(min=8, max = 12, message = "8-12자의 비밀번호를 입력해주세요")
    public String password;
}