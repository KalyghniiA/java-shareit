package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserCreateDto;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserUpdateDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserCreateDto user);

    UserDto updateUser(Long userId, UserUpdateDto userNode);

    void deleteUser(Long userId);

    UserDto getUserById(Long userId);

    List<UserDto> findAll();
}
