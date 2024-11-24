package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Map;

public interface FilmStorage {

    Film save(Film film);

    Film findById(long userId);

    Map<Long, Film> findAll();

    void deleteById(long id);
}