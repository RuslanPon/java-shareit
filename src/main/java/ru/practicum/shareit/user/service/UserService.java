package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import javax.validation.ValidationException;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    UserRepository userRepository;

    public Collection<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUser(long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        checkingEmail(user);
        return userRepository.create(user);
    }

    public User updateUser(long id, User user) {
        checkingEmail(user);
        return userRepository.update(id, user);
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
