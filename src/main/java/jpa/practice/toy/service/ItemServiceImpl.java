package jpa.practice.toy.service;

import jpa.practice.toy.domain.Item;
import jpa.practice.toy.domain.Member;
import jpa.practice.toy.domain.MemberLikeItem;
import jpa.practice.toy.dto.ItemRequest;
import jpa.practice.toy.dto.ItemResponse;
import jpa.practice.toy.repository.ItemRepository;
import jpa.practice.toy.repository.LikeItemRepository;
import jpa.practice.toy.repository.MemberRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final LikeItemRepository likeItemRepository;

    @Override
    public ItemResponse addProduct(ItemRequest request, Member loginMember) {
        // 세션에서 넘어온 Member는 현재 영속 상태가 아님
        // 다시 영속 상태로 만들기 위해 Member의 ID로 DB에서 다시 조회함 todo: 예외 핸들러 설계
        Member member = memberRepository.findById(loginMember.getId())
                .orElseThrow();
        Item item = new Item(request, member);
        ItemResponse itemResponse = new ItemResponse(itemRepository.save(item));
        member.addItem(item);
        return itemResponse;
    }

    @Override
    public ItemResponse likeItem(Long itemId, Member loginMember) {
        // 사용자 객체를 영속 상태로 만들기 위해 한번 더 조회
        Member member = memberRepository.findById(loginMember.getId())
                .orElseThrow();
        // 아이템 객체 조회
        Item item = itemRepository.findById(itemId).orElseThrow();
        // 이미 찜했는지 확인
        Optional<MemberLikeItem> alreadyLike = likeItemRepository.findByMemberAndItem(member, item);
        // 이미 찜한 상태라면 찜 취소
        if (alreadyLike.isPresent()) {
            likeItemRepository.delete(alreadyLike.get());
        } else {
            // 찜하지 않은 상태라면 그대로 저정
            MemberLikeItem likeItem = new MemberLikeItem(item, member);
            likeItemRepository.save(likeItem);
        }
        return new ItemResponse(item);
    }
}
