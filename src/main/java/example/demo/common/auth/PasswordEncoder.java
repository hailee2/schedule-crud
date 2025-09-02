package example.demo.common.auth;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {      //스프링이 해당 클래스를 빈(Bean)으로 등록하게 해줌
    public String encode(String rawPassword){       //문자열(사용자가 입력한 비밀번호)을 입력받아 문자열을 반환하는 메서드
        return BCrypt.withDefaults().hashToString(BCrypt.MIN_COST, rawPassword.toCharArray());
    }
    /*
    BCrypt.withDefaults() -> BCrypt라는 암호화 도구를 기본설정으로 사용
    hashToString() -> 문자열을 암호화해서 해시 문자열로 만듦
    BCrypt.MIN_COST -> 암호화 난이도(연산 횟수)를 최소 -> 빠르지만 보안은 상대적으로 약함
    rawPassword.toCharArray() -> 평문 비밀번호를 문자열에서 문자배열(Char[])로 바꿔서 전달
     */
    public boolean matches(String rawPassword, String encodedPassword){
        BCrypt.Result result = BCrypt.verifyer().verify(rawPassword.toCharArray(),encodedPassword);
        return result.verified;
    }
    /*
    BCryt.verifyer() -> 비밀번호 확인 도구를 꺼내옴
    .verify() -> 입력한 평문 비밀번호(rawPassword)를 암호화된 비밀번호(encodedPassword)와 비교
    rawPassword.toCharArray -> 입력값을 문자배열로 변환하여 전달
    결과를 BCrypt.verified 타입 변수인 result에 담음
    result.verified -> 비교 결과가 맞으면 true, 틀리면 false
     */
}
