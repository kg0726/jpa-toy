package jpa.practice.toy.service;

import jakarta.persistence.EntityManager;
import jpa.practice.toy.domain.Item;
import jpa.practice.toy.domain.Member;
import jpa.practice.toy.domain.MemberLikeItem;
import jpa.practice.toy.dto.ItemRequest;
import jpa.practice.toy.dto.ItemResponse;
import jpa.practice.toy.dto.ItemUpdateRequest;
import jpa.practice.toy.dto.LikeItemListResponse;
import jpa.practice.toy.exception.AuthorizationException;
import jpa.practice.toy.exception.ItemNotFoundException;
import jpa.practice.toy.exception.MemberNotFoundException;
import jpa.practice.toy.repository.ItemRepository;
import jpa.practice.toy.repository.LikeItemRepository;
import jpa.practice.toy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final LikeItemRepository likeItemRepository;
    private final EntityManager em;

    @Override
    public ItemResponse addProduct(ItemRequest request, Member loginMember) {
        // 세션에서 넘어온 Member는 현재 영속 상태가 아님
        // 다시 영속 상태로 만들기 위해 Member의 ID로 DB에서 다시 조회함 todo: 예외 핸들러 설계
        Member member = memberRepository.findById(loginMember.getId())
                .orElseThrow(() -> new MemberNotFoundException());
        Item item = new Item(request, member);
        ItemResponse itemResponse = new ItemResponse(itemRepository.save(item));
        member.addItem(item);
        return itemResponse;
    }

    @Override
    public ItemResponse likeItem(Long itemId, Member loginMember) {
        // 사용자 객체를 영속 상태로 만들기 위해 한번 더 조회
        Member member = memberRepository.findById(loginMember.getId())
                .orElseThrow(() -> new MemberNotFoundException());
        // 아이템 객체 조회
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException());
        ItemResponse response = new ItemResponse(item);
        // 이미 찜했는지 확인
        Optional<MemberLikeItem> alreadyLike = likeItemRepository.findByMemberAndItem(member, item);
        // 이미 찜한 상태라면 찜 취소
        if (alreadyLike.isPresent()) {
            likeItemRepository.delete(alreadyLike.get());
            response.setLike(false);
        } else {
            // 찜하지 않은 상태라면 그대로 저정
            MemberLikeItem likeItem = new MemberLikeItem(item, member);
            likeItemRepository.save(likeItem);
            response.setLike(true);
        }
        return response;
    }

    @Override
    public LikeItemListResponse likeItemList(Member loginMember) {
        // 영속성 컨텍스트에 넘기기 위해 한번 더 DB에서 조회
        Member member = memberRepository.findById(loginMember.getId())
                .orElseThrow(() -> new MemberNotFoundException());

        return new LikeItemListResponse(member.getMemberLikeItems());
    }

    @Override
    public Page<ItemResponse> allItems(Pageable pageable, Member loginMember) {
        // 페이징 처리된 Item 엔티티들을 가져옴
        Page<Item> itemPage = itemRepository.findAll(pageable);
        // 사용자가 찜한 상품 목록에서 Item의 Id만 추출하여 Set으로 만듦
        Set<Long> likedItemId = likeItemRepository.findMemberLikeItemByMemberId(loginMember.getId())
                .orElseThrow(() -> new MemberNotFoundException())
                .stream()
                .map(likeItem -> likeItem.getItem().getId())
                .collect(Collectors.toSet());
        // Page 객체 안의 엔티티들을 DTO로 변환
        return itemPage.map(item -> {
            ItemResponse dto = new ItemResponse(item);
            if (likedItemId.contains(item.getId())) dto.setLike(true);
            return dto;
        });
    }

    // 상품 상세 조회

    @Override
    public ItemResponse getItem(Long id, Member loginMember) {
        // 요청받은 상품을 조회
        Item item = itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException());
        ItemResponse response = new ItemResponse(item);
        // 이미 좋아요가 되어있는지 조회
        Optional<MemberLikeItem> alreadyLike = likeItemRepository.findByMemberAndItem(loginMember, item);
        if (alreadyLike.isPresent()) {
            response.setLike(true);
        }
        return response;
    }

    // 상품 정보 업데이트
    @Override
    public ItemResponse updateItem(Long id, Member loginMember, ItemUpdateRequest request) {
        // 업데이트를 요청한 상품 조회
        Item item = itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException());
        // 만약 해당 상품과 유저 정보가 같지 않다면 권한 없음
        // 영속성 컨텍스트의 동일성을 활용하기 위해 한번 더 조회
        Member member = memberRepository.findById(loginMember.getId()).orElseThrow(() -> new MemberNotFoundException());
        if (! item.getMember().equals(member)) {
            throw new AuthorizationException();
        }
        // 업데이트 실행
        item.update(request);
        ItemResponse response = new ItemResponse(item);
        Optional<MemberLikeItem> alreadyLike = likeItemRepository.findByMemberAndItem(loginMember, item);
        if (alreadyLike.isPresent()) {
            response.setLike(true);
        }
        return response
                ;
    }
}
