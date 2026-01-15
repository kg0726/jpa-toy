package jpa.practice.toy.dto;

import jpa.practice.toy.domain.Member;
import jpa.practice.toy.domain.MemberLikeItem;
import jpa.practice.toy.repository.LikeItemRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class LikeItemListResponse {
    List<LikeList> memberLikeItemList = new ArrayList<>();

    public LikeItemListResponse(List<MemberLikeItem> memberLikeItemList) {
        this.memberLikeItemList = memberLikeItemList.stream()
                .map(LikeList::new)
                .toList();
    }
    // 엔티티를 직접 반환하지 않기 위해 DTO를 생성하여 반환함
    @Getter
    static class LikeList {
        String name;
        Integer price;

        String nickname;
        public LikeList(MemberLikeItem likeItem) {
            this.name = likeItem.getItem().getName();
            this.price = likeItem.getItem().getPrice();
            this.nickname = likeItem.getMember().getNickname();
        }
    }

}
