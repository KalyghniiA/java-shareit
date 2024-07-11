package ru.practicum.shareit.item.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.AccessRightsException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.dao.ItemRepository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dao.UserRepository;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ItemDto getItemById(Long itemId) {
        Item item = itemRepository.getById(itemId).orElseThrow(() -> new NotFoundException("Данного предмета нет в базе"));
        return ItemDto.toItemDto(item);
    }

    @Override
    public ItemDto saveItem(ItemDto item, Long userId) {
        userRepository.getById(userId).orElseThrow(() -> new NotFoundException("Данного пользователя нет в базе"));
        return ItemDto.toItemDto(itemRepository.save(Item.toItem(item), userId));
    }

    @Override
    public ItemDto updateItem(JsonNode itemNode, Long itemId, Long userId) {
        User user = userRepository.getById(userId).orElseThrow(() -> new NotFoundException("Данного пользователя нет в базе"));
        Item item = itemRepository.getById(itemId).orElseThrow(() -> new NotFoundException("Данного предмета нет в базе"));
        if (!item.getOwner().equals(user.getId())) {
            throw new AccessRightsException("Данный пользователь не является владельцем вещи");
        }
        return ItemDto.toItemDto(itemRepository.update(itemNode, itemId));
    }

    @Override
    public void deleteItem(Long itemId) {
        itemRepository.getById(itemId).orElseThrow(() -> new NotFoundException("Данного предмета нет в базе"));
        itemRepository.delete(itemId);
    }

    @Override
    public List<ItemDto> findAllItemByUser(Long userId) {
        userRepository.getById(userId).orElseThrow(() -> new NotFoundException("Данного пользователя нет в базе"));
        return itemRepository.findAllById(userId).stream().map(ItemDto::toItemDto).toList();
    }

    @Override
    public List<ItemDto> getItemsByText(String text, Long userId) {
        userRepository.getById(userId).orElseThrow(() -> new NotFoundException("Данного пользователя нет в базе"));
        return itemRepository.getByText(text.trim().toLowerCase(), userId).stream().map(ItemDto::toItemDto).toList();
    }
}
