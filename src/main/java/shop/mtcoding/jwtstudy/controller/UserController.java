package shop.mtcoding.jwtstudy.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.jwtstudy.config.auth.JwtProvider;
import shop.mtcoding.jwtstudy.model.User;
import shop.mtcoding.jwtstudy.model.UserRepository;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserRepository userRepository;
    private final HttpSession session;

    @GetMapping("/user") // 인증 필요
    public ResponseEntity<?> user() {
        // 권한처리
        return ResponseEntity.ok().body("접근성공");
    }

    @GetMapping("/") // 인증 불 필요
    public ResponseEntity<?> main() {
        return ResponseEntity.ok().body("접근성공");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(User user) {
        Optional<User> userOP = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        if (userOP.isPresent()) {
            String jwt = JwtProvider.create(userOP.get());
            return ResponseEntity.ok().header(JwtProvider.HEADER, jwt).body("로그인 성공");
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
