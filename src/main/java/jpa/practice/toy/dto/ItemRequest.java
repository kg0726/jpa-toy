package jpa.practice.toy.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jpa.practice.toy.domain.CategoryEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemRequest {

    @NotBlank(message = "상품 이름은 필수 정보입니다.")
    private String name;
    @Min(0)
    @NotNull(message = "가격은 필수 정보입니다.")
    private Integer price;

    @Enumerated(EnumType.STRING)
    private CategoryEnum category;
}
