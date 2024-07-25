package ru.practicum.shareit.user.dao;

import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);

    User update(User newUser);

    void delete(Long userId);

    Optional<User> getById(Long userId);

    List<User> findAll();
}
