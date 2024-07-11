package ru.practicum.shareit.item.service;

import com.fasterxml.jackson.databind.JsonNode;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {
    ItemDto getItemById(Long itemId);

    ItemDto saveItem(ItemDto item, Long userId);

    ItemDto updateItem(JsonNode itemNode, Long itemId, Long userId);

    void deleteItem(Long itemId);

    List<ItemDto> findAllItemByUser(Long userId);

    List<ItemDto> getItemsByText(String text, Long userId);
}
