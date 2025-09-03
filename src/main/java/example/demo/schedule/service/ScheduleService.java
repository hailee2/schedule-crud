package example.demo.schedule.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import example.demo.comment.dto.CommentResponse;
import example.demo.comment.entity.QComment;
import example.demo.comment.repository.CommentRepository;
import example.demo.schedule.dto.*;
import example.demo.schedule.entity.QSchedule;
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
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor    //final 접근 제어자로 설정된 필드에 대해서 생성자를 만들어준다.
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final JPAQueryFactory queryFactory;     //queryDSL용 custom repository따로 만들어 사용하기.

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

    //일정 전체조회 1(양방향일 때)
//    @Transactional(readOnly = true)
//    public List<ScheduleGetAllResponse> findSchedules(){
//        return scheduleRepository.findAllWithComments().stream()
//                .map(schedule -> new ScheduleGetAllResponse(
//                    schedule.getId(),
//                    schedule.getUser().getId(),
//                    schedule.getTitle(),
//                    schedule.getContent(),
//                    schedule.getComments().stream()
//                            .map(comment -> new CommentResponse(
//                                comment.getId(),
//                                comment.getUser().getId(),
//                                comment.getContent(),
//                                comment.getCreatedAt(),
//                                comment.getModifiedAt()
//                            )).collect(Collectors.toList()),
//                    schedule.getCreatedAt(),
//                    schedule.getModifiedAt()
//                )).collect(Collectors.toList());
//    }

    // 일정 전체조회2(단방향일때 QueryDSL 사용)
    @Transactional(readOnly = true)
    public List<ScheduleGetAllResponse> findSchedules(Pageable pageable){
        QSchedule schedule = QSchedule.schedule;    //QueryDSL에서 schedule 엔티티를 표현하는 Q클래스
        QComment comment = QComment.comment;        //QueryDSL에서 comment 엔티티를 표현하는 Q클래스

        // 1. Schedule + Comment fetch join
        List<Schedule> schedules = queryFactory     //queryFactory는 QueryDSL 쿼리를 생성/실행하는 핵심 객체
                .selectFrom(schedule)               //조회 시작 대상: schedule 엔티티
                .leftJoin(comment).on(comment.schedule.eq(schedule))    //schedule과 comment를 left join. eq()는 두 컬럼/엔티티가 같은지 조건을 의미 (= 연산자 역할)
                .fetchJoin()    //fetchJoin: 연관 엔티티(comment)를 한 번에 같이 조회해서 N+1 문제 방지
                .distinct()     //조인 시 중복된 schedule 엔티티 제거
                .orderBy(schedule.modifiedAt.desc()) // 최신 수정일 순
                .offset(pageable.getOffset())       // 페이지 시작 위치 | 페이지번호 * 페이지 크기
                .limit(pageable.getPageSize())      // 페이지 크기
                .fetch();       //실제 쿼리를 DB에 날리고 결과를 가져오는 메서드 | 최종 실행 → List<Schedule> 반환

        // 2. Schedule -> DTO 변환
        //⬇️List<Schedule> → List<ScheduleGetAllResponse>
        return schedules.stream()
                .map(s -> {
                    //스케줄에 속한 댓글들을 다시 조회해서 DTO로 변환
                    List<CommentResponse> response = queryFactory
                            //comment 엔티티 선택
                            .selectFrom(comment)
                            //해당 스케줄(s)에 속한 댓글만 조회. eq(s)는 "comment.schedule == s" 조건
                            .where(comment.schedule.eq(s))
                            .fetch()
                            .stream()
                            //Comment → CommentResponse로 변환
                            .map(c -> new CommentResponse(c.getId(), c.getUser().getId(), c.getContent(), c.getCreatedAt(), c.getModifiedAt()
                            ))
                            .collect(Collectors.toList());
                    return new ScheduleGetAllResponse(s.getId(),s.getUser().getId(),s.getTitle(),s.getContent(),response, s.getCreatedAt(),s.getModifiedAt());
                }).collect(Collectors.toList());
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

    //일정 수정
    public ScheduleUpdateResponse updateSchedule(Long userId, ScheduleUpdateRequest request, Long scheduleId){
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "없는 일정입니다.")
        );
        if(!userId.equals(schedule.getUser().getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"게시글은 본인만 수정이 가능합니다");
        }
        schedule.updateSchedule(request.getTitle(), request.getContent());
        return new ScheduleUpdateResponse(
                schedule.getId(),
                schedule.getUser().getId(),
                schedule.getTitle(),
                schedule.getContent(),
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
