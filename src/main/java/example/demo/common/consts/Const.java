package example.demo.common.consts;

//"LOGIN_USER" 라는 세션 키 값을 상수(Constant)로 지정해주는 클래스. LOGIN_USER를 하드코딩하면 오타시 버그, 유지보수에 어려움 -> Const.LOGIN_USER로 쓰면 관리가 쉬움
public abstract class Const {
    public static final String LOGIN_USER = "LOGIN_USER";
}