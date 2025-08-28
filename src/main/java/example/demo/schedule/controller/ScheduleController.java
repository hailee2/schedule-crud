package example.demo.schedule.controller;

import example.demo.schedule.dto.ScheduleGetAllResponse;
import example.demo.schedule.dto.ScheduleGetOneResponse;
import example.demo.schedule.dto.ScheduleSaveRequest;
import example.demo.schedule.dto.ScheduleSaveResponse;
import example.demo.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    //일정 생성
    @PostMapping
    public ResponseEntity<ScheduleSaveResponse> saveSchedule(       //ResponseEntity: HTTP 응답을 객체형태로 표현한 것. 상태코드, 헤더, body를 모두 함께 설정해서 보낼 수 있는 객체
//            @SessionAttribute(name = Const.LOGIN_USER) Long userId,        //나중에 세션에서 로그인 유저 ID 가져올 예정
            @RequestBody ScheduleSaveRequest request                         //요청의 JSON 데이터를 ScheduleSaveRequest로 매핑
    ){
        Long userId = 100L;                                         //하드코딩으로 임시 테스트용 userId 생성
        ScheduleSaveResponse response = scheduleService.saveSchedule(userId, request);      //저장한 일정을 ResponseDto에 담기
        return ResponseEntity.status(HttpStatus.CREATED).body(response);                    //저장이 되었다면 201 created 상태코드 출력 + 저장한 응답값 전달
    }

    //일정 전체조회
    @GetMapping
    public ResponseEntity<List<ScheduleGetAllResponse>> getSchedules(){
        return ResponseEntity.ok(scheduleService.findSchedules());
    }

    //일정 단건조회
    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleGetOneResponse> getSchedule(@PathVariable Long scheduleId){
        return ResponseEntity.ok(scheduleService.findSchedule(scheduleId));
    }

    @DeleteMapping("/{scheduleId}")
    public void deleteSchedule(
//            @SessionAttribute(name = Const.LOGIN_USER) Long userId,                    //Session을 통해 로그인 아이디 받아와야함  //추후 주석 해제
            @PathVariable Long scheduleId
    ){
        Long userId = 100L;         //임시 하드코딩을 통해 테스트용 아이디 생성
        scheduleService.deleteSchedule(userId, scheduleId);
    }
}
