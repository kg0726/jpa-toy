package jpa.practice.toy.repository;

import jpa.practice.toy.domain.Item;
import jpa.practice.toy.domain.Member;
import jpa.practice.toy.domain.MemberLikeItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeItemRepository extends JpaRepository<MemberLikeItem, Long> {
    Optional<MemberLikeItem> findByMemberAndItem(Member member, Item item);
}
