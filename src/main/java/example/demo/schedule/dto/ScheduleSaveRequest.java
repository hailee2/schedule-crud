package example.demo.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ScheduleSaveRequest {
    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 30, message = "제목은 30자 이하로 입력해주세요.")
    public String title;

    @NotBlank(message = "내용은 필수입니다.")
    @Size(max = 500, message = "내용은 500자 이하로 입력해주세요.")
    public String content;
}
