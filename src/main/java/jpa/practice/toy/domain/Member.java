package jpa.practice.toy.domain;

import jakarta.persistence.*;
import jpa.practice.toy.dto.MemberJoinRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String loginId; // 로그인용 아이디

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String town;

    public Member(String loginId, String password, String nickname, String town) {
        this.loginId = loginId;
        this.password = password;
        this.nickname = nickname;
        this.town = town;
    }

    public Member(MemberJoinRequest dto) {
        this.loginId = dto.getLoginId();
        this.town = dto.getTown();
        this.nickname = dto.getNickname();
        this.password = dto.getPassword();
    }
}
