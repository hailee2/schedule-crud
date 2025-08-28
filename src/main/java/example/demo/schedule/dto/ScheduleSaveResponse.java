package example.demo.schedule.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleSaveResponse {
    public final Long id;
    public final Long userId;
    public final String title;
    public final String content;
    public final LocalDateTime createdAt;
    public final LocalDateTime modifiedAt;

    public ScheduleSaveResponse(Long id, Long userId, String title, String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
