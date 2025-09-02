package example.demo.user.repository;

import example.demo.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);       //반환타입을 Optional<User>로 해서 조회결과가 없어도 안전하게 처리 가능 (orElseThrow)

    Boolean existsByEmail(String email);
}

/*
<User,Long> -> 제네릭타입
 제네릭 : 타입을 미리 결정하지 않고 유연하게 쓰지만 사용할 때는 고정. <T>같은 형태로 사용, 실제 객체 만들때 타입을 지정해줌. 타입 안정성 확보, 형변환 필요없음.
 User > 이 Repository가 다룰 entity 클래스
 Long > 이 entity의 Primary Key
 UserRepository는 User 테이블 + PK(Long)전용 CRUD 기능을 제공
 */
