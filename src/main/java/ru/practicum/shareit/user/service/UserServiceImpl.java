package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.ObjectNotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import javax.validation.ValidationException;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public Collection<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new ObjectNotFoundException("User not found");
        }
        return UserMapper.toUserDto(user.get());
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        //checkingEmail(userDto);
        User newUser = userRepository.save(UserMapper.toUser(userDto, new User()));
        log.info("User created" + newUser);
        return UserMapper.toUserDto(newUser);
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        //checkingEmail(userDto);
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new ObjectNotFoundException("User not found");
        }
        UserMapper.toUser(userDto, user.get());
        User updatedUser = userRepository.save(user.get());
        log.info("User updated" + updatedUser);
        return UserMapper.toUserDto(updatedUser);
    }

    @Override
    public void removeUser(Long id) {
        userRepository.deleteById(id);
    }

    private void checkingEmail(UserDto userDto) {
        Collection<User> users = userRepository.findAll();
        boolean us = users.stream().anyMatch(user1 -> user1.getEmail().equals(userDto.getEmail()));
        if (us) {
            throw new ValidationException("The user with this email is already registered");
        }
    }
}
