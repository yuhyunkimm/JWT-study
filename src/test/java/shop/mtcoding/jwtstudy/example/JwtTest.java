package shop.mtcoding.jwtstudy.example;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtTest {

    // 아래는 예시> 나만 알고 있는 시크릿 넘버를 알고 있어야 한다
    // ABC(메타코딩) -> 1313AB
    // ABC(시크) -> 5335KD

    // 아래 토큰을 만들어서 준다 서버는 아래토큰 번호를 풀어보고 동일하면 인증완료가 된다
    // 1313AB -> 토큰
    @Test
    public void createJwt_test() {
        // give

        // when
        String jwt = JWT.create()
                .withSubject("토큰제목")
                // 토큰 만료 기한(기한이 끝나면 재 로그인)
                // currentTimeMillis 현재 시간
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                // 아이디와 1(key)
                .withClaim("id", 1)
                .withClaim("role", "guest")
                // secret키가 들어 가야한다 아래는 대칭키로 누구한테 공개 하지 않고
                // 유저에 아이디와 권한을 가지고 있으면 된다
                .sign(Algorithm.HMAC512("메타코딩"));

        System.out.println(jwt);
        // then
    }

    @Test
    public void VerifyJwt_test() {
        // give
        String jwt = JWT.create()
                .withSubject("토큰제목")
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                .withClaim("id", 1)
                .withClaim("role", "guest")
                .sign(Algorithm.HMAC512("메타코딩"));

        System.out.println(jwt);

        // when
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512("메타코딩")).build().verify(jwt);
            int id = decodedJWT.getClaim("id").asInt();
            String role = decodedJWT.getClaim("role").asString();
            System.out.println(id);
            System.out.println(role);
        } catch (SignatureVerificationException sve) {
            System.out.println("토큰 검증 실패" + sve.getMessage()); // 위조
        } catch (TokenExpiredException tee) {
            System.out.println("토큰 시간 만료" + tee.getMessage()); // 오래됨
        }

        // then
    }
}
