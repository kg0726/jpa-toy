package jpa.practice.toy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class ItemUpdateRequest {

    // 업데이트는 상품의 이름과 가격만 허용
    private String name;
    private Integer price;
}
