package example.demo.user.dto;

import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class UserGetAllResponse {
    private final Long id;
    private final String nickname;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public UserGetAllResponse(Long id, String nickname, String email, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
