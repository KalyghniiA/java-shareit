package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemUpdateDto;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
@Slf4j
public class ItemController {
    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ItemDto postItem(
            @RequestBody @Valid ItemCreateDto item,
            @RequestHeader("X-Sharer-User-Id") @NotNull long userId) {
        log.info("Получен запрос на создание предмета");
        ItemDto newItem = itemService.saveItem(item, userId);
        log.info(String.format("Предмет создан с id %s", newItem.getId()));
        return newItem;
    }

    @GetMapping("{itemId}")
    public ItemDto getItem(@PathVariable long itemId) {
        log.info(String.format("Получен запрос на получение предмета под id %s", itemId));
        ItemDto item = itemService.getItemById(itemId);
        log.info("Предмет отправлен");
        return item;
    }

    @PatchMapping("{itemId}")
    public ItemDto updateItem(
            @RequestBody @Valid ItemUpdateDto itemUpdateDto,
            @PathVariable long itemId,
            @RequestHeader("X-Sharer-User-Id") @NotNull long userId) {
        log.info("Получен запрос на обновление данных о предмете");
        itemUpdateDto.setId(itemId);
        ItemDto newItem = itemService.updateItem(itemUpdateDto, userId);
        log.info("Данные изменены");
        return newItem;
    }

    @GetMapping
    public List<ItemDto> findAllItems(@RequestHeader("X-Sharer-User-Id") @NotNull long userId) {
        log.info("Получен запрос на получение всех предметов");
        List<ItemDto> items = itemService.findAllItemByUser(userId);
        log.info("Предметы отправлены");
        return items;
    }

    @GetMapping("/search")
    public List<ItemDto> getItemsByText(
            @RequestParam String text,
            @RequestHeader("X-Sharer-User-Id") @NotNull long userId) {
        log.info("Получен запрос на получение предметов по тексту");
        List<ItemDto> items = itemService.getItemsByText(text);
        log.info("Предметы отправлены");
        return items;
    }

    @DeleteMapping("{itemId}")
    public void deleteItem(@PathVariable long itemId) {
        log.info("получен запрос на удаление товара");
        itemService.deleteItem(itemId);
        log.info("предмет удален");
    }
}
