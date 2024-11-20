package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        log.info("Добавление нового пользователя.");
        return userService.createUser(user);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User newUser) {
        log.info("Обновление пользователя.");
        return userService.updateUser(newUser);
    }

    @GetMapping
    public Collection<User> findAllUser() {
        log.info("Вывод список пользователей.");
        return userService.findAllUser();
    }

    @PutMapping(value = "/{id}/friends/{friendId}")
    public User addFriend(@NotNull @PathVariable long id,
                          @NotNull @PathVariable long friendId) {
        return userService.addFriend(id, friendId);
    }

    @DeleteMapping(value = "/{id}/friends/{friendId}")
    public User removeFriend(@NotNull @PathVariable long id,
                             @NotNull @PathVariable long friendId) {
        return userService.removeFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getAllFriends(@NotNull @PathVariable long id) {
        return userService.getAllFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getMutualFriends(@NotNull @PathVariable long id,
                                       @NotNull @PathVariable long otherId) {
        return userService.getMutualFriends(id, otherId);
    }

}