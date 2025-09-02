package example.demo.common.auth.service;

import example.demo.common.auth.PasswordEncoder;
import example.demo.common.auth.dto.LoginRequest;
import example.demo.common.auth.dto.UserSaveRequest;
import example.demo.common.auth.dto.UserSaveResponse;
import example.demo.user.entity.User;
import example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //회원가입 서비스
    @Transactional
    public UserSaveResponse signup(UserSaveRequest request){
        if(userRepository.existsByEmail(request.getEmail())){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"이미 존재하는 이메일입니다.");
        }
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = User.of(request.getNickname(), request.getEmail(), encodedPassword);
        userRepository.save(user);
        return new UserSaveResponse(
                user.getId(),
                user.getNickname(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }

    //로그인 서비스
    @Transactional(readOnly = true)
    public Long Login(LoginRequest loginRequest){
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(                //SELECT * FROM users WHERE email=request에서받은이메일 LIMIT 1;
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 이메일이 존재하지 않습니다.")
        );
        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"비밀번호가 일치하지 않습니다.");
        }
        return user.getId();
    }

    /*로그아웃 서비스 : 없어도 됨
    로그아웃은 비즈니스 로직이 아니라 세션 관리
    로그인은 DB에서 유저 찾기 -> 비밀번호 검증 -> 세션저장 ; 비즈니스로직이 들어감
    로그아웃은 단순 서버 메모리에 올라와있는 세션을 없애는(무효화하는)작업. DB조회나 검증이 필요없음.
    */

    //회원 탈퇴 서비스
    @Transactional
    public void deleteUser(Long userId){
//        userRepository.findById(userId).orElseThrow(
//                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 유저 ID입니다."));        //컨트롤러에서 세션을 확인함
        userRepository.deleteById(userId);              //DELETE FROM users WHERE id=userId
    }
}
