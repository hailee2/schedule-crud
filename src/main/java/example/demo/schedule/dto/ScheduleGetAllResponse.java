package example.demo.schedule.dto;

import example.demo.comment.dto.CommentResponse;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ScheduleGetAllResponse {
    private final Long id;
    private final Long userId;
    private final String title;
    private final String content;
    private final List<CommentResponse> comments;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public ScheduleGetAllResponse(Long id, Long userId, String title, String content, List<CommentResponse> comments, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.comments = comments;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
