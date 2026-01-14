package jpa.practice.toy.service;

import jpa.practice.toy.domain.Member;
import jpa.practice.toy.dto.LoginRequest;
import jpa.practice.toy.dto.MemberResponse;
import org.springframework.stereotype.Service;

public interface LoginService {
    public Member login(LoginRequest request);
}
