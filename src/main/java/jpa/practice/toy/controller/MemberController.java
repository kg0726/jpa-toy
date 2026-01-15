package jpa.practice.toy.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jpa.practice.toy.domain.Member;
import jpa.practice.toy.dto.LoginRequest;
import jpa.practice.toy.dto.MemberJoinRequest;
import jpa.practice.toy.dto.MemberJoinResponse;
import jpa.practice.toy.dto.MemberResponse;
import jpa.practice.toy.service.MemberService;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/join")
    public ResponseEntity<MemberJoinResponse> join(@Valid @RequestBody MemberJoinRequest request) {
        MemberJoinResponse response = memberService.join(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 현재 로그인한 사용자의 정보와 해당 사용자가 등록한 상품 정보까지 같이 반환
    @GetMapping
    public ResponseEntity<MemberResponse> getMember(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute("loginMember");
        MemberResponse response = memberService.getMember(loginMember.getId());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
