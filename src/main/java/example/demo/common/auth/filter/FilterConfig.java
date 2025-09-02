package example.demo.common.auth.filter;

import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration          //이 클래스는 스프링 설정 파일임을 표시
public class FilterConfig {

    @Bean       //메서드가 반환하는 객체를 스프링 빈으로 등록
    public FilterRegistrationBean<Filter> loginFilter() {       //서플릿 필터를 등록할 때 쓰는 스프링 부트 전용 도우미(등록기)
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();   //registrationBean 생성
        registrationBean.setFilter(new LoginFilter());  //실제로 동작할 필터 객를 넣음.
        registrationBean.setOrder(1);       //필터 체인 순서 정하기. 숫자가 작을수록 먼저 실행
        registrationBean.addUrlPatterns("/*");  //모든 URL경로에 지정
        return registrationBean;                //설정이 끝난 등록기를 리턴해 스프링이 이걸 받아서 실제 서블릿 컨테이너에 필터로 붙여줌
    }
    /*
    <Filter> -> 이 등록기가 다룰 필터 타입이 Filter(jakarta.servlet.Filter)라는 뜻의 제네릭
     */
}