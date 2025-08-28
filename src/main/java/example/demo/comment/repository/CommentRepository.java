package example.demo.comment.repository;

import example.demo.comment.entity.Comment;
import example.demo.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByScheduleId(Long scheduleId);
}
