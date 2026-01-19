package jpa.practice.toy.service;

import jpa.practice.toy.domain.Member;
import jpa.practice.toy.dto.ItemRequest;
import jpa.practice.toy.dto.ItemResponse;
import jpa.practice.toy.dto.LikeItemListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemService {
    ItemResponse addProduct(ItemRequest request, Member loginMember);

    ItemResponse likeItem(Long itemId, Member loginMember);

    // 사용자가 찜한 상품 반환
    LikeItemListResponse likeItemList(Member loginMember);

    // 모든 등록된 상품 반환
    Page<ItemResponse> allItems(Pageable pageable, Member loginMember);

    // 상품 상세 조회
    ItemResponse getItem(Long id, Member loginMemeber);
}
