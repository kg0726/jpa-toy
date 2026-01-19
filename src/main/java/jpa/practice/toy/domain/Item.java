package jpa.practice.toy.domain;

import jakarta.persistence.*;
import jpa.practice.toy.dto.ItemRequest;
import jpa.practice.toy.dto.ItemUpdateRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @Column(name = "item_name")
    private String name;

    private int price;
    private CategoryEnum category;

    // 이 상품을 누가 등록한 상품인지?(N:1)
    @ManyToOne
    @JoinColumn(name = "member_id")
    @Setter
    private Member member;

    public Item(ItemRequest request, Member loginMember) {
        this.name = request.getName();
        this.price = request.getPrice();
        this.member = loginMember;
        this.category = request.getCategory();
    }

    // 상품 정보를 업데이트하는 메서드
    public void update(ItemUpdateRequest request) {
        if (request.getName() != null && !request.getName().isBlank()) {
            this.name = request.getName();
        }
        if (request.getPrice() != null) {
            this.price = request.getPrice();
        }
    }
}
