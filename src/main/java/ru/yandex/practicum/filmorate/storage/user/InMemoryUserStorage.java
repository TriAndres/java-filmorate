package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("InMemoryUserStorage")
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();

    @Override
    public User save(User user) {
        user.setId(user.getId());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User findById(long id) {
        return users.get(id);
    }

    @Override
    public void deleteById(long id) {
        users.remove(id);
    }
}