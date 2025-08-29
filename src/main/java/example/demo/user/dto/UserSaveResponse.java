package example.demo.user.dto;

import lombok.Getter;

@Getter
public class UserSaveResponse {
    private final Long id;
    private final String nickname;
    private final String email;

    public UserSaveResponse(Long id, String nickname, String email) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
    }
}
