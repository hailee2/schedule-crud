package example.demo.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CommentUpdateRequest {
    @NotBlank
    @Size(max =250, message = "댓글은 250자 이하로 입력해주세요.")
    public String content;
}
