package example.demo.comment.entity;

import example.demo.common.entity.BaseEntity;
import example.demo.schedule.entity.Schedule;
import example.demo.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comments") // 테이블 이름은 comments
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="schedule_id", nullable=false)
    private Schedule schedule;

    @Column(length=250, nullable = false)
    @NotBlank
    private String content;

    public Comment(User user, Schedule schedule, String content){
        this.user = user;
        this.schedule = schedule;
        this.content = content;
    }

    public static Comment of(User user, Schedule schedule, String content){
        return new Comment(user, schedule, content);
    }

    public void updateComment(String content){
        this.content = content;
    }
}