package example.demo.common.auth.controller;

import example.demo.common.auth.dto.LoginRequest;
import example.demo.common.auth.service.AuthService;
import example.demo.user.dto.UserSaveRequest;
import example.demo.user.dto.UserSaveResponse;
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
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }
        return ResponseEntity.ok("로그아웃 성공");
    }
    //회원 탈퇴
    @DeleteMapping("/me")
    public void deleteUser(
            HttpServletRequest request
    ){
        HttpSession session = request.getSession(false);
        Long userId = (Long) session.getAttribute("LOGIN_USER");
        authService.deleteUser(userId);
        session.invalidate();
    }
}
