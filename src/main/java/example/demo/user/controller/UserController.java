package example.demo.user.controller;

import example.demo.user.dto.UserGetAllResponse;
import example.demo.user.dto.UserGetOneResponse;
import example.demo.user.dto.UserSaveRequest;
import example.demo.user.dto.UserSaveResponse;
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
}
