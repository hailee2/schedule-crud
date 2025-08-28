package example.demo.schedule.service;

import example.demo.comment.dto.CommentResponse;
import example.demo.comment.repository.CommentRepository;
import example.demo.schedule.dto.ScheduleGetOneResponse;
import example.demo.schedule.dto.ScheduleSaveRequest;
import example.demo.schedule.dto.ScheduleSaveResponse;
import example.demo.schedule.entity.Schedule;
import example.demo.schedule.repository.ScheduleRepository;
import example.demo.user.entity.User;
import example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor    //final 접근 제어자로 설정된 필드에 대해서 생성자를 만들어준다.
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    //일정생성
    @Transactional
    public ScheduleSaveResponse saveSchedule(Long userId, ScheduleSaveRequest request){
        User user = userRepository.findById(userId).orElseThrow(                            //받아온 유저ID로 User 조회하기
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "없는 유저입니다.")     //없는 유저ID라면 예외처리
        );
        Schedule schedule = Schedule.of(request.getTitle(),request.getContent(),user);      //Schedule 객체 생성(제목,내용,작성자user)
        scheduleRepository.save(schedule);                                                  //생성한 스케쥴을 DB에 저장하기.
        return new ScheduleSaveResponse(                                                    //스케쥴을 Response형태로 반환
                schedule.getId(),
                schedule.getUser().getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    //일정 단건조회
    @Transactional(readOnly = true) //조회 로직이므로 읽기 전용
    public ScheduleGetOneResponse findSchedule(Long scheduleId){                    // 컨트롤러에서 scheduleId 전달받음
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(    // 컨트롤러에서 전달받은 scheduleId로 일정을 조회하고, 결과를 Schedule 타입의 schedule 변수에 할당
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"존재하지 않는 일정입니다.")        //넣은 스케쥴아이디가 존재하지 않을 경우 예외처리
        );
        List<CommentResponse> comments = commentRepository.findAllByScheduleId(scheduleId).stream()  //코멘트repository에서 scheduleId를 입력해 해당 일정의 댓글을 모두 찾아온다.> 지금은 List<comment>, 코멘트 엔티티의 리스트 형식임
                .map(comment -> new CommentResponse (       //stream으로 comment를 commentresponse로 바꿔준다.
                        comment.getId(),
                        comment.getUser().getId(),
                        comment.getContent(),
                        comment.getCreatedAt(),
                        comment.getModifiedAt()
                )).collect(Collectors.toList());                     //원하는 타입이 List<CommentResponse>이므로 리스트로 바꿔준다.

        return new ScheduleGetOneResponse(
                schedule.getId(),
                schedule.getUser().getId(),
                schedule.getTitle(),
                schedule.getContent(),
                comments,                           //스케쥴의 댓글 List<CommentResponse>를 담은 변수
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    //일정 삭제
    @Transactional
    public void deleteSchedule(Long userId, Long scheduleId){
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(                    //입력받은 일정 ID로 일정조회
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 일정입니다."));    //입력받은 일정 ID가 없는 ID면 예외처리

        if(userId.equals(schedule.getUser().getId())){                                               //로그인한 ID와 작성자 ID가 같은지 확인하기
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인의 게시글만 삭제할 수 있습니다.");  //작성자가 본인이 아닐 경우 예외처리
        }
        scheduleRepository.deleteById(scheduleId);
    }
}
