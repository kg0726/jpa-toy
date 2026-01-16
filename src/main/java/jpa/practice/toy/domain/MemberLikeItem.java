package jpa.practice.toy.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


// 한명의 사용자의 여러개의 아이템을 찜 할 수 있고
// 하나의 아이템은 여러 찜한 사용자가 있을 수 있음
// N:M을 풀어내기 위한 중개 테이블
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class MemberLikeItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_like_item_id")
    private Long id;

    // Item 테이블과 연결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    // Member 테이블과 연결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public MemberLikeItem(Item item, Member member) {
        this.item = item;
        this.member = member;
    }
}
