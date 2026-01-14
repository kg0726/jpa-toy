package jpa.practice.toy.service;

import jpa.practice.toy.domain.Member;
import jpa.practice.toy.dto.LoginRequest;
import jpa.practice.toy.dto.MemberJoinRequest;
import jpa.practice.toy.dto.MemberResponse;
import jpa.practice.toy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResponse join(MemberJoinRequest request) {
        // 1. 중복 아이디 검증
        memberRepository.findByLoginId(request.getLoginId())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 아이디입니다.");
                });
        // 2. 엔티티 생성 및 저장
        Member member = new Member(request);
        System.out.println(member.getPassword());
        memberRepository.save(member);
        return new MemberResponse(member);
    }
}
