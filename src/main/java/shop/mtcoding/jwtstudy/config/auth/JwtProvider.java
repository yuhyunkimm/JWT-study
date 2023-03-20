package shop.mtcoding.jwtstudy.config.auth;

public class JwtProvider {

    private static final String SUBJECT = "jwtstudy";
    private static final int EXP = 1000 * 60 * 60;
    private static final String TOKEN_PREFIX = "Bearer "; // 한칸띄위기
    private static final String HEADER = "Authorization";
    private static final String SECRET = "메타코딩";

    public static void create() {
    }

    public static void verify() {
    }
}
