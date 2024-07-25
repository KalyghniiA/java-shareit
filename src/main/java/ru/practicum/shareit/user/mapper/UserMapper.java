package ru.practicum.shareit.user.mapper;

import ru.practicum.shareit.user.dto.UserCreateDto;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserUpdateDto;
import ru.practicum.shareit.user.model.User;

public class UserMapper {

    public User fromUserCreate(UserCreateDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        return user;
    }

    public User fromUserUpdate(UserUpdateDto userDto, User user) {
       User newUser = new User();
       newUser.setId(user.getId());
       newUser.setName(userDto.getName() == null ? user.getName() : userDto.getName());
       newUser.setEmail(userDto.getEmail() == null ? user.getEmail() : userDto.getEmail());
       return newUser;
    }

    public UserDto toUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }
}
