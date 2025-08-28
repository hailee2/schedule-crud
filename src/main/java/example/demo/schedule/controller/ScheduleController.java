package example.demo.schedule.controller;

import example.demo.schedule.dto.ScheduleGetOneResponse;
import example.demo.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping("/{scheduleId}")  //일정 단건조회
    public ResponseEntity<ScheduleGetOneResponse> getSchedule(@PathVariable Long scheduleId){
        return ResponseEntity.ok(scheduleService.findSchedule(scheduleId));
    }
}
