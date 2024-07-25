package ru.practicum.shareit.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.user.dto.UserCreateDto;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserUpdateDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.dao.UserRepository;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper = new UserMapper();

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createUser(UserCreateDto userDto) {
        User user = userMapper.fromUserCreate(userDto);
        return userMapper.toUserDto(userRepository.save(user));
    }

    @Override
    public UserDto updateUser(Long userId, UserUpdateDto userDto) {
        User oldUser = userRepository.getById(userId).orElseThrow(() -> new NotFoundException("Данного пользователя нет в базе"));
        User newUser = fromUserUpdateToUser(userDto, oldUser);
        return userMapper.toUserDto(userRepository.update(newUser));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.getById(userId).orElseThrow(() -> new NotFoundException("Данного пользователя нет в базе"));
        userRepository.delete(userId);
    }

    @Override
    public UserDto getUserById(Long userId) {
        return userMapper.toUserDto(userRepository.getById(userId).orElseThrow(() -> new NotFoundException("Данного пользователя нет в базе")));
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(userMapper::toUserDto).toList();
    }

    private User fromUserUpdateToUser(UserUpdateDto userDto, User user) {
        User newUser = new User();
        newUser.setId(user.getId());
        newUser.setName(userDto.getName() == null ? user.getName() : userDto.getName());
        newUser.setEmail(userDto.getEmail() == null ? user.getEmail() : userDto.getEmail());
        return newUser;
    }
}
