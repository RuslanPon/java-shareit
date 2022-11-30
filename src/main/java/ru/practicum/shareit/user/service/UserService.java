package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import javax.validation.ValidationException;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    UserRepository userRepository;

    public Collection<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    public UserDto getUser(long id) {
        return UserMapper.toUserDto(userRepository.findById(id));
    }

    public UserDto createUser(User user) {
        checkingEmail(user);
        return UserMapper.toUserDto(userRepository.create(user));
    }

    public UserDto updateUser(long id, User user) {
        checkingEmail(user);
        return UserMapper.toUserDto(userRepository.update(id, user));
    }

    public void removeUser(long id) {
        userRepository.remove(id);
    }

    private void checkingEmail(User user) {
        Collection<User> users = userRepository.findAll();
        boolean us = users.stream().anyMatch(user1 -> user1.getEmail().equals(user.getEmail()));
        if (us) {
            throw new ValidationException("The user with this email is already registered");
        }
    }
}
