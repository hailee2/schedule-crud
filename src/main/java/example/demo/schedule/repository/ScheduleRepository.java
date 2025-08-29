package example.demo.schedule.repository;

import example.demo.comment.entity.Comment;
import example.demo.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule,Long> {
//    @EntityGraph(attributePaths = "comments")
//    List<Schedule> findAll(); // @EntityGragh와 커스텀 쿼리메서드를 같이 쓰려면 쿼리문도 적어줘야함. 그냥 findAll()해도 됨.

//    @Query(value = """
//    SELECT s
//    FROM Schedule s
//    JOIN FETCH s.comments
//""")
//    List<Schedule> findAllWithComments();
}