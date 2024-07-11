package ru.practicum.shareit.item.dao;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.*;

@Repository
public class ItemRepositoryMemory implements ItemRepository {
    private final Map<Long, Item> items = new HashMap<>();
    private long currentId = 1L;

    @Override
    public Optional<Item> getById(Long itemId) {
        return Optional.ofNullable(items.get(itemId));
    }

    @Override
    public Item save(Item item, Long userId) {
        item.setId(currentId++);
        item.setOwner(userId);
        items.put(item.getId(), item);
        return item;
    }

    @Override
    public Item update(JsonNode itemNode, Long itemId) {
        Item oldItem = items.get(itemId);
        Item newItem = new Item();
        newItem.setId(oldItem.getId());
        newItem.setName(itemNode.path("name").isMissingNode()
                ? oldItem.getName()
                : itemNode.get("name").asText());
        newItem.setDescription(itemNode.path("description").isMissingNode()
                ? oldItem.getDescription()
                : itemNode.get("description").asText());
        newItem.setAvailable(itemNode.path("available").isMissingNode()
                ? oldItem.getAvailable()
                : itemNode.get("available").asBoolean());
        newItem.setOwner(itemNode.path("owner").isMissingNode()
                ? oldItem.getOwner()
                : itemNode.get("owner").asLong());

        items.replace(itemId, newItem);
        return newItem;
    }

    @Override
    public void delete(Long itemId) {
        items.remove(itemId);
    }

    @Override
    public List<Item> findAllById(Long userId) {
        return items.values().stream().filter(item -> item.getOwner().equals(userId)).toList();
    }

    @Override
    public List<Item> getByText(String text, Long userId) {
        if (text.isBlank()) {
            return new ArrayList<>();
        }
        return items.values().stream()
                .filter(item -> item.getName().toLowerCase().contains(text) || item.getDescription().toLowerCase().contains(text))
                .filter(Item::getAvailable)
                .toList();
    }
}
