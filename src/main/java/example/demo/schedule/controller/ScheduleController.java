package example.demo.schedule.controller;

import example.demo.common.consts.Const;
import example.demo.schedule.dto.*;
import example.demo.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor //**@Autowired 찾아보기**
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    //일정 생성
    @PostMapping
    public ResponseEntity<ScheduleSaveResponse> saveSchedule(       //ResponseEntity: HTTP 응답을 객체형태로 표현한 것. 상태코드, 헤더, body를 모두 함께 설정해서 보낼 수 있는 객체
        @SessionAttribute(name = Const.LOGIN_USER) Long userId,        //나중에 세션에서 로그인 유저 ID 가져올 예정
        @RequestBody ScheduleSaveRequest request                         //요청의 JSON 데이터를 ScheduleSaveRequest로 매핑
    ){
        ScheduleSaveResponse response = scheduleService.saveSchedule(userId, request);      //저장한 일정을 ResponseDto에 담기
        return ResponseEntity.status(HttpStatus.CREATED).body(response);                    //저장이 되었다면 201 created 상태코드 출력 + 저장한 응답값 전달
    }

    //일정 전체조회
    @GetMapping
    public ResponseEntity<List<ScheduleGetAllResponse>> getSchedules(
            @RequestParam(defaultValue = "0") int page,     //0번째 페이지
            @RequestParam(defaultValue = "5") int size      //한페이지 5개
    ){
        Pageable pageable = PageRequest.of(page,size);
        List<ScheduleGetAllResponse> schedules = scheduleService.findSchedules(pageable);
        return ResponseEntity.ok(schedules);
    }

    //일정 단건조회
    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleGetOneResponse> getSchedule(@PathVariable Long scheduleId){
        return ResponseEntity.ok(scheduleService.findSchedule(scheduleId));
    }

    //일정 수정
    @PutMapping("/{scheduleId}")
    public ResponseEntity<ScheduleUpdateResponse> updateSchedule(
            @SessionAttribute(name = Const.LOGIN_USER) Long userId,
            @RequestBody ScheduleUpdateRequest request,
            @PathVariable Long scheduleId
    ){
        return ResponseEntity.ok(scheduleService.updateSchedule(userId, request, scheduleId));
    }

    //일정삭제
    @DeleteMapping("/{scheduleId}")
    public void deleteSchedule(
            @SessionAttribute(name = Const.LOGIN_USER) Long userId,                    //Session을 통해 로그인 아이디 받아와야함  //추후 주석 해제
            @PathVariable Long scheduleId
    ){
        scheduleService.deleteSchedule(userId, scheduleId);
    }
}
