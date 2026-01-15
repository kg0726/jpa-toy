package jpa.practice.toy.service;

import jpa.practice.toy.domain.Item;
import jpa.practice.toy.domain.Member;
import jpa.practice.toy.dto.ItemRequest;
import jpa.practice.toy.dto.ItemResponse;
import jpa.practice.toy.repository.ItemRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;

    @Override
    public ItemResponse addProduct(ItemRequest request, Member loginMember) {
        Item item = new Item(request, loginMember);
        return new ItemResponse(itemRepository.save(item));
    }
}
