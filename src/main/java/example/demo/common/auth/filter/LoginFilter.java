package example.demo.common.auth.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@Slf4j      //롬복이 log.info()같은 로그 객체를 자동 주입. log라는 변수가 자동으로 생겨서 로그를 찍을 수 있음. 디버깅할 때 요청 URI,세션 상태 등을 찍기 좋음
public class LoginFilter implements Filter {    //서블릿 필터 표준 인터페이스를 구현, 모든 HTTP 요청은 컨트롤러에 가기 전에 이 필터를 거쳐야함.
    //인증없이 통과시킬 목록 : 세션이 필요없는 회원가입,로그인,로그아웃이 이에 해당함.
    private static final String[] WHITE_LIST = {"/", "/auth/signup", "/auth/login", "auth/logout"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        //HTTP전용 객체로 캐스팅 : 서블릿의 일반 타입을 HTTP 요청/응답으로 바꿔 기능을 더 쓸수 있게 함.
        HttpServletRequest httpRequest  = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        //요청 URI추출 쿼리스트링은 포함하지 않음.
        String requestURI = httpRequest.getRequestURI();

        // 화이트리스트에 포함되지 않은 경우, 세션검사 시작!
        if (!isWhiteList(requestURI)) {
            //기존 세션만 가져오기 (없으면 null, 새로 만들지 않음)
            HttpSession session = httpRequest.getSession(false);    //getSession(true)가 기본값 : 세션이 있으면 가져오고 없으면 만들어줌 / false : 세션이 있으면 가져오고 없으면 null리턴 없으면 새로 안만듦.
            if (session == null || session.getAttribute("LOGIN_USER") == null) {
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인 해주세요.");   //세션이 없거나 세션에 로그인표식("LOGIN_USER)이 없으면 401 보냄
                return; //요청 흐름을 즉시 중단 . 컨트롤러로 못가게.
            }
        }
        chain.doFilter(request, response);  //검사 통과했을 경우 요청을 계속 진행시킴. 이 줄이 호출되어야 컨트롤러까지 도달 가능.
    }

    //화이트리스트 판별 로직
    private boolean isWhiteList(String requestURI) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }
    /*
    simpleMatch는 와일드카드 (*,?)도 지원 -> "/auth/*" -> /auth/login, auth/signup 모두 매칭
     */
}
/*
서블릿이란?
자바 웹 서버에서 요청과 응답을 처리하는 기본 단위
웹 서버(톰캣 같은 것) -> 클라이언트(브라우저) 요청을 받으면 ServletRequest와 ServletResponse 객체를 만들어서 서블릿에 전달.

ServletRequest = 요청 데이터를 담는 객체
ServletResponse = 응답 데이터를 담는 객체
 */