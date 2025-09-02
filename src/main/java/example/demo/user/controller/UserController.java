package example.demo.user.controller;

import example.demo.common.consts.Const;
import example.demo.user.dto.*;
import example.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController                 //@Controller + @ResponseBody
@RequiredArgsConstructor        //final이 붙은 필드값을 생성자로 주입해서 객체 만들어줌 .
@RequestMapping("/users")    //공통 URI를 앞에 묶어줌
public class UserController {
    private final UserService userService;

    //유저 생성 -> auth 회원가입 controller로 이동
//    @PostMapping
//    public ResponseEntity<UserSaveResponse> saveUser(@RequestBody UserSaveRequest request){
//        UserSaveResponse response = userService.saveUser(request);
//        return ResponseEntity.status(HttpStatus.CREATED).body(response);
//    }

    //유저 단건 조회
    @GetMapping("/{userId}")
    public ResponseEntity<UserGetOneResponse> getUser(@PathVariable Long userId){
        return ResponseEntity.ok(userService.findUser(userId));
    }

    //유저 전체 조회
    @GetMapping
    public ResponseEntity<List<UserGetAllResponse>> getUsers(){
        return ResponseEntity.ok(userService.findUsers());
    }

    //유저 수정
    @PutMapping("/me")
    public ResponseEntity<UserUpdateResponse> updateUser(
            @SessionAttribute(name = Const.LOGIN_USER) Long userId, //세션에 저장되어있는 값을 컨트롤러의 메서드 파라미터로 자동으로 꺼내줌. 세션에 LOGIN_USER라는 이름으로 저장된 객체를 꺼내서 Long userId에 넣어줌
            @RequestBody UserUpdateRequest request //RequestBody가 JSON을 UserUpdateRequest 객체로 자동변환해줌.
    ){
        return ResponseEntity.ok(userService.updateUser(userId, request));
    }

    //유저 삭제 -> auth 회원탈퇴 controller로 이동
//    @DeleteMapping("/me")
//    public void deleteUser(
//            @SessionAttribute(name = Const.LOGIN_USER) Long userId
//    ){
//        userService.deleteUser(userId);
//    }
}
