package ru.practicum.shareit.user.service;

import com.fasterxml.jackson.databind.JsonNode;
import ru.practicum.shareit.user.User;

import java.util.List;

public interface UserService {
    User createUser(User user);

    User updateUser(Long userId, JsonNode userNode);

    void deleteUser(Long userId);

    User getUserById(Long userId);

    List<User> findAll();
}
