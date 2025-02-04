package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@Valid @RequestBody User user) {
        log.info("createUser(user)");
        return userService.createUser(user);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User newUser) {
        log.info("updateUser(newUser)");
        return userService.updateUser(newUser);
    }

    @GetMapping
    public Collection<User> findAllUser() {
        log.info("findAllUser()");
        return userService.findAllUser();
    }

    @GetMapping("/{userId}")
    public Optional<User> findByIdUser(@PathVariable long userId) {
        log.info("getByIdUser(userId)");
        return userService.findByIdUser(userId);
    }

    @DeleteMapping("/{id}")
    public void deleteByIdUser(@NotNull @PathVariable long id) {
        log.info("deleteByIdUser(id)");
        userService.deleteById(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addFriend(@NotNull @PathVariable long id,
                          @NotNull @PathVariable long friendId) {
        log.info("addFriend(id, friendId)");
        return userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User removeFriend(@NotNull @PathVariable long id,
                             @NotNull @PathVariable long friendId) {
        log.info("removeFriend(id, friendId)");
        return userService.removeFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public Collection<User> getAllFriends(@NotNull @PathVariable long id) {
        log.info("getAllFriends(id)");
        return userService.getAllFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<User> getMutualFriends(@NotNull @PathVariable long id,
                                             @NotNull @PathVariable long otherId) {
        log.info("getMutualFriends(id,otherId)");
        return userService.getMutualFriends(id, otherId);
    }
}