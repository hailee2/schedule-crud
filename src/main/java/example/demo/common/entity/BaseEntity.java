package example.demo.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter //클래스의 필드 getter를 자동생성해줌
@MappedSuperclass //공통 맵핑정보가 필요할때 사용 (여기선 created_at, modified_at)
@EntityListeners(AuditingEntityListener.class)// 이 엔티티에서 특정 이벤트 발생할 때 리스너를 발생시킴 (시간 자동관리해주는 리스너)
public abstract class BaseEntity{

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedAt;
}