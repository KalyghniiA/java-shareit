package ru.practicum.shareit.user.service;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dao.UserRepository;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        for (User elem: userRepository.findAll()) {
            if (elem.getEmail().equals(user.getEmail())) {
                throw new ValidationException("Такой email уже есть в базе");
            }
        }
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long userId, JsonNode userNode) {
        userRepository.getById(userId).orElseThrow(() -> new NotFoundException("Данного пользователя нет в базе"));
        String email = userNode.path("email").isMissingNode() ? null : userNode.get("email").asText();
        if (email != null) {
            for (User user: userRepository.findAll()) {
                if (email.equals(user.getEmail()) && !user.getId().equals(userId)) {
                    throw new ValidationException("Такой email уже есть в базе");
                }
            }
        }
        return userRepository.update(userId, userNode);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.getById(userId).orElseThrow(() -> new NotFoundException("Данного пользователя нет в базе"));
        userRepository.delete(userId);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.getById(userId).orElseThrow(() -> new NotFoundException("Данного пользователя нет в базе"));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
