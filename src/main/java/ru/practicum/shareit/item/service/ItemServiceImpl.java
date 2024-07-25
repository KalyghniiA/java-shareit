package ru.practicum.shareit.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.AccessRightsException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.dao.ItemRepository;
import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemUpdateDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.dao.UserRepository;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ItemMapper itemMapper = new ItemMapper();

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ItemDto getItemById(Long itemId) {
        Item item = itemRepository.getById(itemId).orElseThrow(() -> new NotFoundException("Данного предмета нет в базе"));
        return itemMapper.toItemDto(item);
    }

    @Override
    public ItemDto saveItem(ItemCreateDto itemDto, Long userId) {
        User user = userRepository.getById(userId).orElseThrow(() -> new NotFoundException("Данного пользователя нет в базе"));
        Item item = itemMapper.fromCreateItemDto(itemDto);
        item.setOwner(user);
        return itemMapper.toItemDto(itemRepository.save(item));
    }

    @Override
    public ItemDto updateItem(ItemUpdateDto itemDto, Long userId) {
        User user = userRepository.getById(userId).orElseThrow(() -> new NotFoundException("Данного пользователя нет в базе"));
        Item item = itemRepository.getById(itemDto.getId()).orElseThrow(() -> new NotFoundException("Данного предмета нет в базе"));
        if (!item.getOwner().getId().equals(user.getId())) {
            throw new AccessRightsException("Данный пользователь не является владельцем вещи");
        }

        return itemMapper.toItemDto(itemRepository.update(fromUpdateItemDtoToItem(itemDto, item)));
    }

    @Override
    public void deleteItem(Long itemId) {
        itemRepository.getById(itemId).orElseThrow(() -> new NotFoundException("Данного предмета нет в базе"));
        itemRepository.delete(itemId);
    }

    @Override
    public List<ItemDto> findAllItemByUser(Long userId) {
        userRepository.getById(userId).orElseThrow(() -> new NotFoundException("Данного пользователя нет в базе"));
        return itemRepository.findByUserId(userId).stream().map(itemMapper::toItemDto).toList();
    }

    @Override
    public List<ItemDto> getItemsByText(String text) {
        return itemRepository.searchBy(text.trim().toLowerCase()).stream().map(itemMapper::toItemDto).toList();
    }

    private Item fromUpdateItemDtoToItem(ItemUpdateDto itemUpdateDto, Item oldItem) {
        Item item = new Item();
        item.setId(oldItem.getId());
        item.setName(itemUpdateDto.getName() == null ? oldItem.getName() : itemUpdateDto.getName());
        item.setDescription(itemUpdateDto.getDescription() == null ? oldItem.getDescription() : itemUpdateDto.getDescription());
        item.setAvailable(itemUpdateDto.getAvailable() == null ? oldItem.getAvailable() : itemUpdateDto.getAvailable());
        item.setOwner(oldItem.getOwner());
        item.setRequest(oldItem.getRequest());
        return item;
    }
}
