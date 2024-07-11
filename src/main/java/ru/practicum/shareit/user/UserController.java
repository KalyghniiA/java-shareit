package ru.practicum.shareit.user;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    public User createUser(@RequestBody @Valid User user) {
        log.info("Получен запрос на создание пользователя");
        User newUser = userService.createUser(user);
        log.info("Новый пользователь создан");
        return newUser;
    }

    @GetMapping("{userId}")
    public User getUserById(@PathVariable Long userId) {
        log.info(String.format("Получен запрос на получение пользователя по id %s", userId));
        User user = userService.getUserById(userId);
        log.info("Пользователь отправлен");
        return user;
    }

    @PatchMapping("{userId}")
    public User updateUser(@PathVariable Long userId, @RequestBody JsonNode userNode) {
        log.info("Получен запрос на изменение пользователя");
        User newUser = userService.updateUser(userId, userNode);
        log.info("Пользователь изменен");
        return newUser;
    }

    @GetMapping
    public List<User> findAllUser() {
        log.info("Получен запрос на получение всех пользователей");
        List<User> users = userService.findAll();
        log.info("Пользователи отправлены");
        return users;
    }

    @DeleteMapping("{userId}")
    public void deleteUser(@PathVariable Long userId) {
        log.info(String.format("Получен запрос на удаление пользователя c id %s", userId));
        userService.deleteUser(userId);
        log.info("Пользователь удален");
    }
}
