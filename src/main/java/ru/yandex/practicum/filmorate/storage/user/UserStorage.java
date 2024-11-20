package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {
    User save(User user);

    boolean containsKey(long id);

    User getById(long userId);

    Collection<User> getByAll();

    void deleteId(long id);

    void deleteAll();
}