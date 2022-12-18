package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.Collection;

public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto updateUser(Long id, UserDto userDto);

    Collection<UserDto> getAllUsers();

    UserDto getUser(Long id);

    void removeUser(Long id);
}
