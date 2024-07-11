package ru.practicum.shareit.user.dao;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepositoryMemory implements UserRepository {
    private final Map<Long, User> users = new HashMap<>();
    private long currentId = 1L;


    @Override
    public User save(User user) {
        user.setId(currentId++);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User update(Long userId, JsonNode userNode) {
        User oldUser = users.get(userId);
        User newUser = new User();
        newUser.setId(oldUser.getId());
        newUser.setName(
                userNode.path("name").isMissingNode() ? oldUser.getName() : userNode.get("name").asText()
        );
        newUser.setEmail(
                userNode.path("email").isMissingNode() ? oldUser.getEmail() : userNode.get("email").asText()
        );
        users.replace(userId, newUser);
        return newUser;
    }

    @Override
    public void delete(Long userId) {
        users.remove(userId);
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
