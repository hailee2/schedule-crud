package example.demo.user.entity;

import example.demo.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10, unique = true, nullable = false)
    @NotBlank
    private String nickname;

    @Column(length = 30, unique = true, nullable = false)
    @NotBlank
    private String email;

    @Column(length = 50, nullable = false)
    @NotBlank
    private String password;

    public User(String nickname, String email, String password){
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    public static User of(String nickname, String email, String password){
        return new User(nickname, email, password);
    }
}