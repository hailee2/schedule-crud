package example.demo.schedule.entity;

import example.demo.common.entity.BaseEntity;
import example.demo.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED) //매개변수없는 기본 생성자를 자동생성해줌
@Table(name = "schedules")
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(length = 30, nullable = false)
    @NotBlank
    private String title;

    @Column(length = 500, nullable = false)
    @NotBlank
    private String content;

    private Schedule(String title, String content, User user){
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public static Schedule of(String title, String content, User user){
        return new Schedule(title, content, user);
    }

    public void updateSchedule(String title, String content){
        this.title=title;
        this.content=content;
    }
}
