package shop.mtcoding.jwtstudy.config.auth;

import java.sql.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import shop.mtcoding.jwtstudy.model.User;

public class JwtProvider {

    private static final String SUBJECT = "jwtstudy";
    private static final int EXP = 1000 * 60 * 60;
    public static final String TOKEN_PREFIX = "Bearer "; // 한칸띄위기
    public static final String HEADER = "Authorization";
    private static final String SECRET = "메타코딩";

    public static String create(User user) {
        String jwt = JWT.create()
                .withSubject(SUBJECT)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXP))
                .withClaim("id", user.getId())
                .withClaim("role", user.getRole())
                .sign(Algorithm.HMAC512("메타코딩"));

        return jwt;
    }

    public static DecodedJWT verify(String jwt) throws SignatureVerificationException, TokenExpiredException {

        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512("메타코딩")).build().verify(jwt);
        // int id = decodedJWT.getClaim("id").asInt();
        // String role = decodedJWT.getClaim("role").asString();
        // System.out.println(id);
        // System.out.println(role);

        // System.out.println("토큰 검증 실패" + sve.getMessage()); // 위조
        // System.out.println("토큰 시간 만료" + tee.getMessage()); // 오래됨

        return decodedJWT;
    }
}
