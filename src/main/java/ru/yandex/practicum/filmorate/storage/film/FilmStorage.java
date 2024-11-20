package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {

    Film save(Film film);

    boolean containsKey(long id);

    Film getById(long userId);

    Collection<Film> getByAll();

    void deleteId(long id);

    void deleteAll();
}