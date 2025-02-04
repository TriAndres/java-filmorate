package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserDoesNotExistException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserStorage userStorage;

    public User createUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            log.info("Name для отображения не может быть пустым — в таком случае будет использован логин");
            user.setName(user.getLogin());
        }
        log.info("Добавление нового пользователя.");
        user.setId(getNextId());
        return userStorage.save(user);
    }

    public User updateUser(User newUser) {
        if (newUser.getId() == null) {
            log.error("Id должен быть указан");
            throw new ValidationException("Id должен быть указан");
        }
        if (userStorage.findAll().contains(userStorage.findById(newUser.getId()))) {
            log.info("Обновление пользователя.");
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

    public Collection<User> findAllUser() {
        log.info("Вывод список пользователей.");
        return userStorage.findAll();
    }

    public Optional<User> findByIdUser(long id) {
        return Optional.ofNullable(userStorage.findById(id));
    }

    public void deleteById(long id) {
        userStorage.deleteById(id);
    }

    public User addFriend(long id, long friendId) {
        if (!(userStorage.findAll().contains(userStorage.findById(id)) && userStorage.findAll().contains(userStorage.findById(friendId)))) {
            log.error("Отсутствует пользователь");
            throw new UserDoesNotExistException("Отсутствует пользователь.");
        }
        User user = userStorage.findById(id);
        User userFriend = userStorage.findById(friendId);
        log.info("Пользователи: 1{},2{}", user, userFriend);
        user.getFriends().add(friendId);
        userFriend.getFriends().add(id);
        userStorage.save(user);
        userStorage.save(userFriend);
        log.info("Добавлен пользователь 1:{}, 2:{}", user.getFriends(), userFriend.getFriends());
        return user;
    }

    public User removeFriend(long id, long friendId) {
        if (!(userStorage.findAll().contains(userStorage.findById(id)) && userStorage.findAll().contains(userStorage.findById(friendId)))) {
            log.error("Отсутствует пользователь");
            throw new UserDoesNotExistException("Отсутствует пользователь.");
        }
        User user = userStorage.findById(id);
        User userFriend = userStorage.findById(friendId);
        log.info("Пользователи: 1{},2{}", user, userFriend);
        user.getFriends().remove(friendId);
        userFriend.getFriends().remove(id);
        userStorage.save(user);
        userStorage.save(userFriend);
        log.info("Список друзей после удаления: user: {}, userFriend: {}",
                user.getFriends(), userFriend.getFriends());
        return user;
    }

    public Collection<User> getAllFriends(long id) {
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

    public Collection<User> getMutualFriends(long id, long otherId) {
        User user = userStorage.findById(id);
        User userFriend = userStorage.findById(otherId);
        if (user == null || userFriend == null) {
            log.info("Нету одного из пользователей");
            throw new UserDoesNotExistException("Нету одного из пользователей");
        }
        return user.getFriends().stream()
                .filter(idUser -> userFriend.getFriends().contains(idUser))
                .map(userStorage::findById)
                .collect(Collectors.toList());
    }

    private Long getNextId() {
        long currentMaxId = userStorage.findAll()
                .stream()
                .mapToLong(User::getId)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}