package ru.practicum.shareit.item.dao;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.*;

@Repository
public class MemoryItemRepository implements ItemRepository {
    private final Map<Long, Item> items = new HashMap<>();
    private final Map<Long, List<Item>> itemsForUser = new HashMap<>();
    private long currentId = 1L;

    @Override
    public Optional<Item> getById(Long itemId) {
        return Optional.ofNullable(items.get(itemId));
    }

    @Override
    public Item save(Item item) {
        item.setId(currentId++);
        items.put(item.getId(), item);
        itemsForUser.computeIfAbsent(item.getOwner().getId(), k -> new ArrayList<>()).add(item);
        return item;
    }

    @Override
    public Item update(Item item) {
        Item oldItem = items.get(item.getId());
        items.replace(item.getId(), item);
        itemsForUser.get(item.getOwner().getId()).remove(oldItem);
        itemsForUser.get(item.getOwner().getId()).add(item);
        return item;
    }

    @Override
    public void delete(Long itemId) {
        Item item = items.remove(itemId);
        if (item != null) {
            itemsForUser.get(item.getOwner().getId()).remove(item);
        }
    }

    @Override
    public List<Item> findByUserId(Long userId) {
        return itemsForUser.get(userId);
    }

    @Override
    public List<Item> searchBy(String text) {
        if (text.isBlank()) {
            return new ArrayList<>();
        }
        return items.values().stream()
                .filter(item -> item.getName().toLowerCase().contains(text) || item.getDescription().toLowerCase().contains(text))
                .filter(Item::getAvailable)
                .toList();
    }
}
