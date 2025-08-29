package example.demo.schedule.dto;

import example.demo.comment.dto.CommentResponse;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ScheduleUpdateResponse {
    private final Long id;
    private final Long userId;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public ScheduleUpdateResponse(Long id, Long userId, String title, String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
