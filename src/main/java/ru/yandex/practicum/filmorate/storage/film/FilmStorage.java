package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {
    Film save(Film film);

    Collection<Film> findAll();

    Film findById(long id);

    void deleteById(Long id);
}