package example.demo.common.auth.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserSaveResponse {
    private final Long id;
    private final String nickname;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public UserSaveResponse(Long id, String nickname, String email, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
