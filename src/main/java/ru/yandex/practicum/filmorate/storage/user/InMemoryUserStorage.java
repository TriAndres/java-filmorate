package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();

    @Override
    public User save(User user) {
        return users.put(user.getId(), user);
    }

    @Override
    public User findById(long userId) {
        return users.get(userId);
    }

    @Override
    public Map<Long, User> findAll() {
        return users;
    }

    @Override
    public void deleteById(long id) {
        users.remove(id);
    }
}