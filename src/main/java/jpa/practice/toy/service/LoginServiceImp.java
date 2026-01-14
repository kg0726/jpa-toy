package jpa.practice.toy.service;

import jpa.practice.toy.domain.Member;
import jpa.practice.toy.dto.LoginRequest;
import jpa.practice.toy.dto.MemberResponse;
import jpa.practice.toy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginServiceImp implements LoginService{
    private final MemberRepository memberRepository;

    @Override
    public Member login(LoginRequest request) {
        // 1. 아이디로 회원을 조회함
        Member findMember = memberRepository.findByLoginId(request.getLoginId())
                .orElseThrow(); // todo 예외 핸들러
        // 해당 회원의 비밀번호와 일치하는지 확인
        if (findMember.getPassword().equals(request.getPassword())) {
            return findMember;
        }
        return null;
    }
}
