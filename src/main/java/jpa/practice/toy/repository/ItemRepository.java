package jpa.practice.toy.repository;

import jpa.practice.toy.domain.Item;
import jpa.practice.toy.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    // 어떤 유저가 등록한 상품을 모두 조회함
    List<Item> findByMember(Member member);
}
