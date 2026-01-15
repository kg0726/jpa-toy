package jpa.practice.toy.dto;

import jpa.practice.toy.domain.Item;
import jpa.practice.toy.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class MemberResponse {

    private String loginId;
    private String nickname;
    private String town;

//    private List<Item> itemList = new ArrayList<>();
    // dto에서 엔티티를 직접 반환하는 대신 DTO를 사용함
    private List<ItemDetail> itemList = new ArrayList<>();

    public MemberResponse(Member member) {
        this.loginId = member.getLoginId();
        this.nickname = member.getNickname();
        this.town = member.getTown();
        // 엔티티 리스트를 DTO 리스트로 변환함
        this.itemList = member.getItemList().stream()
                .map(ItemDetail::new)
                .toList();
        // 위 코드를 for문으로 풀면 밑의 코드와 같음
//        for (Item item : member.getItemList()) {
//            ItemDetail detail = new ItemDetail(item);
//            itemList.add(detail);
//        }

    }
    @Getter
    static class ItemDetail {
        private String name;
        private Integer price;

        public ItemDetail(Item item) {
            this.name = item.getName();
            this.price = item.getPrice();
        }
    }
}
