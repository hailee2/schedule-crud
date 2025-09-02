package example.demo.user.entity;

import example.demo.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)       //같은 패키지 안이나 상속받은 클래스만 기본생성자 호출을 할 수 있음. 외부에서 new User() 불가.
@Table(name = "users")
public class User extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10, unique = true, nullable = false)       //닉네임 최대10글자, 중복값혀용하지않음, null허용하지않음
    @NotBlank
    private String nickname;

    @Column(length = 30, unique = true, nullable = false)
    @NotBlank
    private String email;

    @Column(length = 120, nullable = false)
    @NotBlank
    private String password;

    protected User(String nickname, String email, String password){
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    public static User of(String nickname, String email, String password){
        return new User(nickname, email, password);
    }

    public void userUpdate(String nickname, String password){
        this.nickname = nickname;
        this.password = password;
    }
}