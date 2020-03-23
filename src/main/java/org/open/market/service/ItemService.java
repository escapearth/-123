package org.open.market.service;

import lombok.RequiredArgsConstructor;
import org.open.market.exception.NotEnoughStockException;
import org.open.market.model.dto.ItemDto;
import org.open.market.model.items.Category;
import org.open.market.model.items.Item;
import org.open.market.model.items.ItemCategory;
import org.open.market.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public Item saveItem(ItemDto itemDto) {
        Item item = itemDto.from();
        List<ItemCategory> itemCategories = item.getItemCategories();

        for (ItemCategory itemCategory : itemCategories) {
            itemCategory.setItem(item);
            Category category = itemCategory.getCategory();
            category.getItemCategories().add(itemCategory);
        }

        return itemRepository.save(item);
    }

    @Transactional
    public void removeStock(Long itemId, int quantity) {
        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);

        int currentStock = item.getStockQuantity() - quantity;
        if (currentStock < 0) {
            throw new NotEnoughStockException("SOLD OUT");
        }

        item.setStockQuantity(currentStock);
    }


}
