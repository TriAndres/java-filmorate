package ru.yandex.practicum.filmorate.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.UserDoesNotExistException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final InMemoryUserStorage userStorage;

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            log.info("Name для отображения не может быть пустым — в таком случае будет использован логин");
            user.setName(user.getLogin());
        }
        user.setId(getNextId());
        userStorage.save(user);
        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User newUser) {
        if (newUser.getId() == null) {
            log.error("Id должен быть указан");
            throw new ValidationException("Id должен быть указан");
        }
        if (userStorage.findAll().containsKey(newUser.getId())) {
            User oldUser = userStorage.findById(newUser.getId());
            oldUser.setEmail(newUser.getEmail());
            oldUser.setLogin(newUser.getLogin());
            oldUser.setName(newUser.getName());
            oldUser.setBirthday(newUser.getBirthday());
            return newUser;
        }
        log.error("Пользователь с id = {} не найден", newUser.getId());
        throw new UserDoesNotExistException("Пользователь с id = " + newUser.getId() + " не найден");
    }

    @GetMapping
    public Collection<User> findAllUser() {
        return userStorage.findAll().values();
    }

    public User addFriend(long id, long friendId) {
        if (!(userStorage.findAll().containsKey(id) && userStorage.findAll().containsKey(friendId))) {
            log.error("Отсутствует пользователь");
            throw new UserDoesNotExistException("Отсутствует пользователь");
        }
        User user = userStorage.findById(id);
        User userFriend = userStorage.findById(friendId);
        log.info("Пользователи: 1{},2{}", user, userFriend);
        userFriend.addFriend(id);
        user.addFriend(friendId);
        log.info("Добавлен пользователь 1:{}, 2:{}", user.getFriends(), userFriend.getFriends());
        return user;
    }

    public User removeFriend(Long id, Long friendId) {
        if (!(userStorage.findAll().containsKey(id) && userStorage.findAll().containsKey(friendId))) {
            throw new UserDoesNotExistException("Отсутствует пользователь");
        }
        User user = userStorage.findById(id);
        User userFriend = userStorage.findById(friendId);
        log.info("Пользователи: 1{},2{}", user, userFriend);
        user.removeFriend(friendId);
        userFriend.removeFriend(id);
        log.info("Список друзей после удаления: user: {}, userFriend: {}",
                user.getFriends(), userFriend.getFriends());
        return user;
    }

    public List<User> getAllFriends(long id) {
        User user = userStorage.findById(id);
        if (user == null) {
            throw new UserDoesNotExistException("Такого пользователя нет");
        }
        Set<Long> userFriends = user.getFriends();
        log.info("Список друзей {}", userFriends);

        return userFriends.stream()
                .map(userStorage::findById)
                .collect(Collectors.toList());
    }

    public List<User> getMutualFriends(long id, long otherId) {
        User user = userStorage.findById(id);
        User userFriend = userStorage.findById(otherId);
        if (user == null || userFriend == null) {
            log.error("Нету одного из пользователей");
            throw new UserDoesNotExistException("Нету одного из пользователей ");
        }
        return user.getFriends().stream()
                .filter(idUser -> userFriend.getFriends().contains(idUser))
                .map(userStorage::findById)
                .collect(Collectors.toList());
    }

    private Long getNextId() {
        long currentMaxId = userStorage.findAll().values()
                .stream()
                .mapToLong(User::getId)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}