package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemUpdateDto;

import java.util.List;

public interface ItemService {
    ItemDto getItemById(Long itemId);

    ItemDto saveItem(ItemCreateDto item, Long userId);

    ItemDto updateItem(ItemUpdateDto itemDto, Long userId);

    void deleteItem(Long itemId);

    List<ItemDto> findAllItemByUser(Long userId);

    List<ItemDto> getItemsByText(String text);
}
