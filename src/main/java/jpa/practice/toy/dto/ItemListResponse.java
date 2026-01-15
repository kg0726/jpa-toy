package jpa.practice.toy.dto;

import jpa.practice.toy.domain.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class ItemListResponse {

    List<Item> itemList;

    public ItemListResponse(List<Item> itemList) {
        this.itemList = itemList;
    }
}
