package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmDoesNotExistException;
import ru.yandex.practicum.filmorate.exception.UserDoesNotExistException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public Film createFilm(Film film) {
        log.info("Добавление нового фильма.");
        film.setId(getNextId());
        return filmStorage.save(film);
    }

    public Film updateFilm(Film newFilm) {
        if (newFilm.getId() == null) {
            log.error("Не указан id");
            throw new ValidationException("Id должен быть указан");
        }
        if (filmStorage.findAll().contains(filmStorage.findById(newFilm.getId()))) {
            Film oldFilm = filmStorage.findById(newFilm.getId());
            log.info("Обновление фильма.");
            oldFilm.setName(newFilm.getName());
            oldFilm.setDescription(newFilm.getDescription());
            oldFilm.setReleaseDate(newFilm.getReleaseDate());
            oldFilm.setDuration(newFilm.getDuration());
            filmStorage.save(newFilm);
        }
        log.error("Фильм с id = {} не найден", newFilm.getId());
        throw new FilmDoesNotExistException("Фильм с id = " + newFilm.getId() + " не найден");
    }

    public Collection<Film> findAllFilm() {
        log.info("Вывод список фильмов.");
        return filmStorage.findAll();
    }

    public Optional<Film> findByIdFilm(long id) {
        return Optional.ofNullable(filmStorage.findById(id));
    }

    public void deleteById(long id) {
        filmStorage.deleteById(id);
    }

    public Film addLike(long id, long userId) {
        if (!filmStorage.findAll().contains(filmStorage.findById(id))) {
            log.error("Отсутствует фильм.");
            throw new FilmDoesNotExistException("Отсутствует фильм.");
        }
        if (!userStorage.findAll().contains(userStorage.findById(userId))) {
            log.error("Отсутствует пользователь.");
            throw new FilmDoesNotExistException("Отсутствует пользователь.");
        }
        Film film = filmStorage.findById(id);
        film.getLikes().add(userId);
        filmStorage.save(film);
        log.info("Лайк добавлен {}", film.getLikes());
        return film;
    }

    public Film deleteLike(long id, long userId) {
        Film film = filmStorage.findById(id);
        User user = userStorage.findById(userId);
        if (film == null || user == null) {
            log.info("Отсутствует пользователь.");
            throw new UserDoesNotExistException("Отсутствует пользователь.");
        }
        film.getLikes().remove(userId);
        filmStorage.save(film);
        log.info("Лайк удалён {}", film.getLikes());
        return film;
    }

    public List<Film> getPopularFilms(int count) {
        return filmStorage.findAll().stream()
                .filter(film -> film.getLikes() != null)
                .sorted((f1, f2) -> Integer.compare(f2.getLikes().size(), f1.getLikes().size()))
                .limit(count)
                .collect(Collectors.toList());
    }

    private Long getNextId() {
        long currentMaxId = filmStorage.findAll()
                .stream()
                .mapToLong(Film::getId)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}