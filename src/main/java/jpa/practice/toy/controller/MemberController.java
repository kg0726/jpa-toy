package jpa.practice.toy.controller;

import jakarta.validation.Valid;
import jpa.practice.toy.dto.LoginRequest;
import jpa.practice.toy.dto.MemberJoinRequest;
import jpa.practice.toy.dto.MemberResponse;
import jpa.practice.toy.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/join")
    public ResponseEntity<MemberResponse> join(@Valid @RequestBody MemberJoinRequest request) {
        MemberResponse response = memberService.join(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
