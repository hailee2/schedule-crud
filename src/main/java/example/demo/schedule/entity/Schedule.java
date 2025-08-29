package example.demo.schedule.entity;

import example.demo.comment.dto.CommentResponse;
import example.demo.comment.entity.Comment;
import example.demo.common.entity.BaseEntity;
import example.demo.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

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

//    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Comment> comments = new ArrayList<>();     // new ArrayList<>(); 하는 이유 : 초기화 안하면 null값이라 컴파일오류뜸.
    //양방향 관계에서 외래키를 갖고있는 쪽이 주인. 여기선 Comment가 주인
    //mappedBy가 없으면 Schedule 테이블에도 외래키를 생성하려고함. -> 중복컬럼 생성됨. mappedBy해서 schedule은 읽기전용 역할만 하도록 함.
    //cascade는 주인 엔티티와 연동된다고 생각하면 됨. 댓글에 변화가 생기면 일정에도 변화가 생김.
    //orphanRemoval은 주인 엔티티가 삭제되면 고아객체가되니까 고아객체가되면 삭제해주는 기능.

    private Schedule(String title, String content, User user){
        this.title = title;
        this.content = content;
        this.user = user;
       // this.comments = comments;
    }

    public static Schedule of(String title, String content, User user){
        return new Schedule(title, content, user); //양방향일 때 엔티티에 comment 있으므로 생성자 매개변수에 List<Comments> comment 추가해줘야함
    }

//    public static Schedule ofWithComments(String title, String content, User user, List<Comment> comments){
//        return new Schedule(title, content, user, comments);
//    }

    public void updateSchedule(String title, String content){
        this.title=title;
        this.content=content;
    }
}
