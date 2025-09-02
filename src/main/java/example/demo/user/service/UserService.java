package example.demo.user.service;

import example.demo.common.auth.PasswordEncoder;
import example.demo.user.dto.*;
import example.demo.user.entity.User;
import example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //유저 생성 -> Auth 회원가입 서비스로 이동
//    @Transactional
//    public UserSaveResponse saveUser(UserSaveRequest request){
//        String encodedPassword = passwordEncoder.encode(request.getPassword());
//        User user = User.of(request.getNickname(), request.getEmail(), encodedPassword);
//        User savedUser = userRepository.save(user);
//        return new UserSaveResponse(
//                savedUser.getId(),
//                savedUser.getNickname(),
//                savedUser.getEmail(),
//                savedUser.getCreatedAt(),
//                savedUser.getModifiedAt()
//        );
//    }

    //유저 조회
    @Transactional(readOnly = true)
    public UserGetOneResponse findUser(Long userId){
        User user = userRepository.findById(userId).orElseThrow(            //findById() -> select * from users where id=userId
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"존재하지 않는 유저ID 입니다.")
        );
        return new UserGetOneResponse(
                user.getId(),
                user.getNickname(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }

    //유저 전체 조회 : 필요한가? 관리자 말고는 필요없지만 일단 연습용으로 생성해보기!
    @Transactional(readOnly = true)
    public List<UserGetAllResponse> findUsers(){
        return userRepository.findAll().stream()            //findAll() -> select * from users
                .map(user -> new UserGetAllResponse(
                        user.getId(),
                        user.getNickname(),
                        user.getEmail(),
                        user.getCreatedAt(),
                        user.getModifiedAt()
                )).collect(Collectors.toList());
    }

    //유저 수정
    @Transactional
    public UserUpdateResponse updateUser(Long userId, UserUpdateRequest request){
        User user = userRepository.findById(userId).orElseThrow(                                    //findByID로 엔티티 가져오면 JPA가 영속성 컨텍스트에 객체를 등록
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 유저 ID입니다.")
        );
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        user.userUpdate(request.getNickname(), encodedPassword);     //엔티티의 필드가 바뀌면서 영속성컨텍스트가 변경사항을 추적(dirty checking) -> Transactional끝날때 자동으로 update쿼리를 만들어 DB에 반영
        return new UserUpdateResponse(
                user.getId(),
                user.getNickname(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }

    //유저 삭제 -> auth 회원 탈퇴 서비스로 이동
//    @Transactional
//    public void deleteUser(Long userId){
//        userRepository.findById(userId).orElseThrow(
//                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 유저 ID입니다."));
//        userRepository.deleteById(userId);
//    }
}
