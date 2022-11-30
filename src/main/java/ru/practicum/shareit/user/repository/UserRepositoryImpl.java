package ru.practicum.shareit.user.repository;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.model.User;

import java.util.*;

@Component
public class UserRepositoryImpl implements UserRepository {
    private static final Map<Long, User> users = new HashMap<>();
    private int idUserCounter = 1;

    @Override
    public Collection<User> findAll() {
        return users.values();
    }

    @Override
    public User findById(long id) {
        return users.get(id);
    }

    @Override
    public User create(User user) {
        user.setId(idUserCounter++);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User update(long id, User user) {
        User updatedUser = findById(id);
        updatedUser.setId(id);
        if (user.getName() != null) {
            updatedUser.setName(user.getName());
        }
        if (user.getEmail() != null) {
            updatedUser.setEmail(user.getEmail());
        }
        users.put(id, updatedUser);
        return updatedUser;
    }

    @Override
    public void remove(long id) {
        users.remove(id);
    }
}
