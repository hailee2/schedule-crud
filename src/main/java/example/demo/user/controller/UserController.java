package example.demo.user.controller;

import example.demo.user.dto.*;
import example.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController         //@Controller + @ResponseBody
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    //유저 생성
    @PostMapping
    public ResponseEntity<UserSaveResponse> saveUser(UserSaveRequest request){
        UserSaveResponse response = userService.saveUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

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
            //            @SessionAttribute(name = Const.LOGIN_USER) Long userId,
            @RequestBody UserUpdateRequest request
    ){
        Long userId = 100L;
        return ResponseEntity.ok(userService.updateUser(userId, request));
    }

    //유저 삭제
    @DeleteMapping("/me")
    public void deleteUser(
            //            @SessionAttribute(name = Const.LOGIN_USER) Long userId,
    ){
        Long userId = 100L;
        userService.deleteUser(userId);
    }
}
