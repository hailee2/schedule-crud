package example.demo.comment.dto;

import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class CommentGetAllResponse {
    private final Long id;
    private final Long userId;
    private final Long scheduleId;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public CommentGetAllResponse(Long id, Long userId, Long scheduleId, String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.userId = userId;
        this.scheduleId = scheduleId;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
