package ru.practicum.shareit.item.mapper;

import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemUpdateDto;
import ru.practicum.shareit.item.model.Item;

public class ItemMapper {
    public Item fromCreateItemDto(ItemCreateDto itemDto) {
        Item item = new Item();
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setAvailable(itemDto.getAvailable());
        return item;
    }

    public Item fromUpdateItemDto(ItemUpdateDto itemUpdateDto, Item oldItem) {
        Item item = new Item();
        item.setId(oldItem.getId());
        item.setName(itemUpdateDto.getName() == null ? oldItem.getName() : itemUpdateDto.getName());
        item.setDescription(itemUpdateDto.getDescription() == null ? oldItem.getDescription() : itemUpdateDto.getDescription());
        item.setAvailable(itemUpdateDto.getAvailable() == null ? oldItem.getAvailable() : itemUpdateDto.getAvailable());
        item.setOwner(oldItem.getOwner());
        item.setRequest(oldItem.getRequest());
        return item;
    }

    public ItemDto toItemDto(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getId());
        itemDto.setName(item.getName());
        itemDto.setDescription(item.getDescription());
        itemDto.setAvailable(item.getAvailable());
        return itemDto;
    }
}
