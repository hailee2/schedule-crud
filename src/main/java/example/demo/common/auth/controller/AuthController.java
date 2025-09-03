package example.demo.common.auth.controller;

import example.demo.common.auth.dto.LoginRequest;
import example.demo.common.auth.service.AuthService;
import example.demo.common.auth.dto.UserSaveRequest;
import example.demo.common.auth.dto.UserSaveResponse;
import example.demo.common.consts.Const;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<UserSaveResponse> signup(
            @RequestBody UserSaveRequest request
    ){
        UserSaveResponse response = authService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    //로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody LoginRequest loginRequest,
            HttpServletRequest request
    ){
        Long userId = authService.Login(loginRequest);
        HttpSession session = request.getSession(); //신규 세션 생성, JSESSIONID 쿠키 발급
        session.setAttribute("LOGIN_USER",userId);  //서버 메모리에 세션 저장

        return ResponseEntity.ok("로그인 성공");
    }
    //로그아웃
    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            HttpServletRequest request
    ){
        HttpSession session = request.getSession(false);    //세션이 없으면 새로 생성하지 않음. 세션이 없으면 null 반환.
        if(session != null){
            session.invalidate();           //현재 세션 무효화.
        }
        return ResponseEntity.ok("로그아웃 성공");
    }
    //회원 탈퇴
    @DeleteMapping("/me")
    public void deleteUser(
            HttpServletRequest request
    ){
        HttpSession session = request.getSession(false);
        Long userId = (Long) session.getAttribute(Const.LOGIN_USER);    //getAttribute가 반환하는 타입은 Object
        authService.deleteUser(userId);
        session.invalidate();
    }
}
