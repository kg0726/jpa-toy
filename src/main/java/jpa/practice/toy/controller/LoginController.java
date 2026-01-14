package jpa.practice.toy.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jpa.practice.toy.domain.Member;
import jpa.practice.toy.dto.LoginRequest;
import jpa.practice.toy.dto.MemberResponse;
import jpa.practice.toy.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        Member loginMember = loginService.login(loginRequest);

        // 로그인이 되었는지 확인
        if (loginMember == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디 또는 비밀번호 확인");
        }
        // 세션이 있으면 세션 반환, 없으면 신규 세션을 생성
        HttpSession session = request.getSession();
        // 세션에 로그인 회원 정보 보관
        session.setAttribute("loginMember", loginMember);
        return ResponseEntity.status(HttpStatus.OK).body("로그인 성공");
    }

    @PostMapping("logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        // 사용자의 로그아웃 요청을 받아 세션 삭제
        HttpSession session = request.getSession(false);
        if (session != null) {
            // 세션을 만료시킴
            session.invalidate();
        }
        return ResponseEntity.status(HttpStatus.OK).body("로그아웃 성공");
    }

}
