package example.demo.comment.service;

import example.demo.comment.dto.*;
import example.demo.comment.entity.Comment;
import example.demo.comment.repository.CommentRepository;
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
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    //코멘트 생성
    @Transactional
    public CommentSaveResponse save(Long userId, Long scheduleId, CommentSaveRequest request){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"존재하지 않는 유저입니다.")
        );
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"없는 일정입니다.")
        );
        Comment comment = Comment.of(user, schedule, request.getContent());
        commentRepository.save(comment);
        return new CommentSaveResponse(
                comment.getId(),
                user.getId(),
                schedule.getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }

    //코멘트 전체 조회
    @Transactional(readOnly = true)
    public List<CommentGetAllResponse> getComments(Long scheduleId){
        return commentRepository.findAllByScheduleId(scheduleId).stream()
                .map(comment -> new CommentGetAllResponse(
                        comment.getId(),
                        comment.getUser().getId(),
                        comment.getSchedule().getId(),
                        comment.getContent(),
                        comment.getCreatedAt(),
                        comment.getModifiedAt()
                )).collect(Collectors.toList());
    }

    //코멘트 단건 조회
    @Transactional(readOnly = true)
    public CommentGetOneResponse getComment(Long commentId){
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 댓글입니다.")
        );
        return new CommentGetOneResponse(
                comment.getId(),
                comment.getUser().getId(),
                comment.getSchedule().getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }

    //코멘트 수정
    @Transactional
    public CommentResponse updateComment(Long userId, Long commentId, CommentUpdateRequest request){
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"존재하지 않는 댓글입니다.")
            );
        if(!comment.getUser().getId().equals(userId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"작성자 본인만 댓글을 수정할 수 있습니다.");
        }
        comment.updateComment(request.getContent());
        return new CommentResponse(
                comment.getId(),
                comment.getUser().getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }

    //코멘트 삭제
    @Transactional
    public void deleteComment(Long userId, Long commentId){
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"존재하지 않는 댓글입니다.")
        );
        if(!comment.getUser().getId().equals(userId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"작성자 본인만 댓글을 삭제할 수 있습니다.");
        }
        commentRepository.deleteById(commentId);
    }
}
