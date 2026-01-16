package jpa.practice.toy.dto;

import jpa.practice.toy.domain.Item;
import jpa.practice.toy.domain.Member;
import jpa.practice.toy.domain.MemberLikeItem;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
public class ItemListResponse {

    private List<ItemResponse> itemList = new ArrayList<>();

    public ItemListResponse(List<Item> itemList,  Set<Long> likedItemId) {
//        this.itemList = itemList.stream()
//                .map(ItemResponse::new)
//                .toList();
        for (Item item : itemList) {
            ItemResponse itemResponse = new ItemResponse(item);
            // 해당 유저가 좋아요 한 상품이라면
            if (likedItemId.contains(item.getId())) itemResponse.setLike(true);
            this.itemList.add(itemResponse);
        }
    }
}
