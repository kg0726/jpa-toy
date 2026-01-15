package jpa.practice.toy.dto;

import jakarta.validation.constraints.NotBlank;
import jpa.practice.toy.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberJoinResponse {
    private String loginId;

    private String nickname;

    private String town;

    public MemberJoinResponse(Member member) {
        this.loginId = member.getLoginId();
        this.nickname = member.getNickname();
        this.town = member.getTown();
    }
}
