package jpa.practice.toy.service;

import jpa.practice.toy.domain.Item;
import jpa.practice.toy.domain.Member;
import jpa.practice.toy.dto.ItemRequest;
import jpa.practice.toy.dto.ItemResponse;
import jpa.practice.toy.repository.ItemRepository;
import jpa.practice.toy.repository.MemberRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

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
}
