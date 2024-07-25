package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserCreateDto;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserUpdateDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping(path = "/users")
@Slf4j
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserDto createUser(@RequestBody @Valid UserCreateDto user) {
        log.info("Получен запрос на создание пользователя");
        UserDto newUser = userService.createUser(user);
        log.info("Новый пользователь создан");
        return newUser;
    }

    @GetMapping("{userId}")
    public UserDto getUserById(@PathVariable long userId) {
        log.info(String.format("Получен запрос на получение пользователя по id %s", userId));
        UserDto user = userService.getUserById(userId);
        log.info("Пользователь отправлен");
        return user;
    }

    @PatchMapping("{userId}")
    public UserDto updateUser(@PathVariable long userId, @RequestBody UserUpdateDto userNode) {
        log.info("Получен запрос на изменение пользователя");
        UserDto newUser = userService.updateUser(userId, userNode);
        log.info("Пользователь изменен");
        return newUser;
    }

    @GetMapping
    public List<UserDto> findAllUser() {
        log.info("Получен запрос на получение всех пользователей");
        List<UserDto> users = userService.findAll();
        log.info("Пользователи отправлены");
        return users;
    }

    @DeleteMapping("{userId}")
    public void deleteUser(@PathVariable long userId) {
        log.info(String.format("Получен запрос на удаление пользователя c id %s", userId));
        userService.deleteUser(userId);
        log.info("Пользователь удален");
    }
}
