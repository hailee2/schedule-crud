package example.demo.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserUpdateRequest {

    @NotBlank(message = "별명 입력은 필수입니다.")
    @Size(max = 10, message = "별명은 10자 이하로 입력해주세요.")
    public String nickname;

    @NotBlank(message = "암호 입력은 필수입니다.")
    @Size(min=8, max = 12, message = "8-12자의 비밀번호를 입력해주세요")
    public String password;
}
