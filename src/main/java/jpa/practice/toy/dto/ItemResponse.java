package jpa.practice.toy.dto;

import jakarta.validation.constraints.NotBlank;
import jpa.practice.toy.domain.Item;
import jpa.practice.toy.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemResponse {

    private String name;
    private int price;
    private Member member;

    public ItemResponse(Item item) {
        this.name = item.getName();
        this.price = item.getPrice();
        this.member = item.getMember();
    }
}
