package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import javax.validation.Valid;
import java.util.Collection;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {
    @Autowired
    private final UserService userService;

    @GetMapping
    public Collection<UserDto> getAllUsers() {
        log.info("getAllUsers");
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Long id) {
        log.info(String.format("getUser for id: %s", id));
        return userService.getUser(id);
    }

    @PostMapping()
    public UserDto createUser(@Valid @RequestBody UserDto userDto) {
        log.info(String.format("createUser for object: %s", userDto));
        return userService.createUser(userDto);
    }

    @PatchMapping("/{userId}")
    public UserDto updateUser(@PathVariable Long userId, @RequestBody UserDto userDto) {
        log.info(String.format("updateUser for object: %s", userDto));
        return userService.updateUser(userId, userDto);
    }

    @DeleteMapping("/{userId}")
    public void removeUser(@PathVariable Long userId) {
        log.info(String.format("removeUser for id: %s", userId));
        userService.removeUser(userId);
    }
}
