package example.demo.common.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration      //스프링 설정 클래스 | 클라스 안에 정의된 @Bean을 보고 스프링이 직접 객체(@Bean)생성해서 IoC컨테이너에 등록해줌
public class QuerydslConfig {
    @PersistenceContext //영속성 컨텍스트를 스프링이 주입
    private EntityManager entityManager;

    @Bean   //메서드의 반환 객체를 Bean으로 등
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }
}
