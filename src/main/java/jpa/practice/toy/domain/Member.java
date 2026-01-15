package jpa.practice.toy.domain;

import jakarta.persistence.*;
import jpa.practice.toy.dto.MemberJoinRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    // 양방향 연관관계 매핑
    @OneToMany(mappedBy = "member")
    List<Item> itemList = new ArrayList<>();
    // 연관관계 편의 메서드
    public void addItem(Item item) {
        this.itemList.add(item);
        // 무한루프 방지
        if (item.getMember() != this) {
            // 상품 객체에도 나(member)를 설정해줌
            item.setMember(this);
        }
    }
}
