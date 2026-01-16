package jpa.practice.toy.repository;

import jpa.practice.toy.domain.Item;
import jpa.practice.toy.domain.Member;
import jpa.practice.toy.domain.MemberLikeItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeItemRepository extends JpaRepository<MemberLikeItem, Long> {
    Optional<MemberLikeItem> findByMemberAndItem(Member member, Item item);
    // 사용자 id를 통해 해당 사용자가 좋아요한 모든 상품 조회
    Optional<List<MemberLikeItem>> findMemberLikeItemByMemberId(Long memberId);
}
