package jpa.practice.toy.service;

import jpa.practice.toy.domain.Item;
import jpa.practice.toy.domain.Member;
import jpa.practice.toy.dto.ItemRequest;
import jpa.practice.toy.dto.ItemResponse;

public interface ItemService {
    ItemResponse addProduct(ItemRequest request, Member loginMember);

    ItemResponse likeItem(Long itemId, Member loginMember);
}
