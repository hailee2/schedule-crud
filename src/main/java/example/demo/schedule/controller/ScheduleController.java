package example.demo.schedule.controller;

import example.demo.schedule.dto.ScheduleGetOneResponse;
import example.demo.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping("/{scheduleId}")  //일정 단건조회
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
