package ru.practicum.shareit.user.dao;


import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exceptions.NotValidEmailException;
import ru.practicum.shareit.user.model.User;

import java.util.*;

@Repository
public class MemoryUserRepository implements UserRepository {
    private final Map<Long, User> users = new HashMap<>();
    private long currentId = 1L;
    private final Set<String> emails = new HashSet<>();


    @Override
    public User save(User user) {
        if (emails.contains(user.getEmail())) {
            throw new NotValidEmailException("Пользователь с данным email уже есть в базе");
        }
        user.setId(currentId++);
        users.put(user.getId(), user);
        emails.add(user.getEmail());
        return user;
    }

    @Override
    public User update(User newUser) {
        String oldEmail = users.get(newUser.getId()).getEmail();
        emails.remove(oldEmail);
        if (emails.contains(newUser.getEmail())) {
            throw new NotValidEmailException("Пользователь с данным email уже есть в базе");
        }
        users.replace(newUser.getId(), newUser);
        emails.add(newUser.getEmail());
        return newUser;
    }

    @Override
    public void delete(Long userId) {
        User user = users.remove(userId);
        emails.remove(user.getEmail());
    }

    @Override
    public Optional<User> getById(Long userId) {
        return Optional.ofNullable(users.get(userId));
    }

    @Override
    public List<User> findAll() {
        return users.values().stream().toList();
    }
}
