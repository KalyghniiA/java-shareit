package ru.practicum.shareit.item.dao;


import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {
    Optional<Item> getById(Long itemId);

    Item save(Item item);

    Item update(Item item);

    void delete(Long itemId);

    List<Item> findByUserId(Long userId);

    List<Item> searchBy(String text);
}
