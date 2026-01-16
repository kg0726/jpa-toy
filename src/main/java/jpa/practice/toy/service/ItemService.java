package jpa.practice.toy.service;

import jpa.practice.toy.domain.Item;
import jpa.practice.toy.domain.Member;
import jpa.practice.toy.dto.ItemListResponse;
import jpa.practice.toy.dto.ItemRequest;
import jpa.practice.toy.dto.ItemResponse;
import jpa.practice.toy.dto.LikeItemListResponse;

public interface ItemService {
    ItemResponse addProduct(ItemRequest request, Member loginMember);

    ItemResponse likeItem(Long itemId, Member loginMember);

    // 사용자가 찜한 상품 반환
    LikeItemListResponse likeItemList(Member loginMember);

    // 모든 등록된 상품 반환
    ItemListResponse allItems(Member loginMember);
}
