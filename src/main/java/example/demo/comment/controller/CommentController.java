package example.demo.comment.controller;

import example.demo.comment.dto.*;
import example.demo.comment.service.CommentService;
import example.demo.common.consts.Const;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<CommentSaveResponse> saveComment(
            @SessionAttribute(name= Const.LOGIN_USER) Long userId,
            @PathVariable Long scheduleId,
            @RequestBody CommentSaveRequest request
    ){
        CommentSaveResponse response = commentService.save(userId, scheduleId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<List<CommentGetAllResponse>> findComments(
            @PathVariable Long scheduleId
    ){
        return ResponseEntity.ok(commentService.getComments(scheduleId));
    }

    @GetMapping("/comments/{commentId}")
    public ResponseEntity<CommentGetOneResponse> findComment(
            @PathVariable Long commentId
    ){
        return ResponseEntity.ok(commentService.getComment(commentId));
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(
            @SessionAttribute(name = Const.LOGIN_USER) Long userId,
            @PathVariable Long commentId,
            @RequestBody CommentUpdateRequest request
    ){
        return ResponseEntity.ok(commentService.updateComment(userId, commentId, request));
    }

    @DeleteMapping("/comments/{commentId}")
    public void deleteComment(
            @SessionAttribute(name = Const.LOGIN_USER) Long userId,
            @PathVariable Long commentId
    ){
        commentService.deleteComment(userId, commentId);
    }
}
