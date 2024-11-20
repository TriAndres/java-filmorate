package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> films = new HashMap<>();

    @Override
    public Film save(Film film) {
        return films.put(film.getId(), film);
    }

    @Override
    public boolean containsKey(long id) {
        return films.containsKey(id);
    }

    @Override
    public Film getById(long userId) {
        return films.get(userId);
    }

    @Override
    public Collection<Film> getByAll() {
        return films.values();
    }

    @Override
    public void deleteId(long id) {
        films.remove(id);
    }

    @Override
    public void deleteAll() {
        films.clear();
    }
}