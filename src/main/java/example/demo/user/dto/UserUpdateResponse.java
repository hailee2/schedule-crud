package example.demo.user.dto;

import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class UserUpdateResponse {
    private final Long id;
    private final String nickname;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public UserUpdateResponse(Long id, String nickname, String email, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
