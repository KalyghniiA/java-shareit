package ru.practicum.shareit.item.dao;

import com.fasterxml.jackson.databind.JsonNode;
import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {
    Optional<Item> getById(Long itemId);

    Item save(Item item, Long userId);

    Item update(JsonNode itemNode, Long itemId);

    void delete(Long itemId);

    List<Item> findAllById(Long userId);

    List<Item> getByText(String text, Long userId);
}
