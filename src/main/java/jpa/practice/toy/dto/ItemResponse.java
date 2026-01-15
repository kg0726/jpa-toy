package jpa.practice.toy.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jpa.practice.toy.domain.CategoryEnum;
import jpa.practice.toy.domain.Item;
import jpa.practice.toy.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemResponse {

    private String name;
    private int price;
    @Enumerated(EnumType.STRING)
    private CategoryEnum category;
    private MemberJoinResponse memberJoinResponse;


    public ItemResponse(Item item) {
        this.name = item.getName();
        this.price = item.getPrice();
        this.memberJoinResponse = new MemberJoinResponse(item.getMember());
        this.category = item.getCategory();
    }
}
