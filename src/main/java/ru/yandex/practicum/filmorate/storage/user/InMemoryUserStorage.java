package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
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
    public boolean containsKey(long id) {
        return users.containsKey(id);
    }

    @Override
    public User getById(long userId) {
        return users.get(userId);
    }

    @Override
    public Collection<User> getByAll() {
        return users.values();
    }

    @Override
    public void deleteId(long id) {
        users.remove(id);
    }

    @Override
    public void deleteAll() {
        users.clear();
    }
}