package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

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
    public Film findById(long userId) {
        return films.get(userId);
    }

    @Override
    public Map<Long, Film> findAll() {
        return films;
    }

    @Override
    public void deleteById(long id) {
        films.remove(id);
    }
}