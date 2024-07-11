package ru.practicum.shareit.user.dao;

import com.fasterxml.jackson.databind.JsonNode;
import ru.practicum.shareit.user.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);

    User update(Long userId, JsonNode userNode);

    void delete(Long userId);

    Optional<User> getById(Long userId);

    List<User> findAll();
}
