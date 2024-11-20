package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
@Slf4j
public class FilmController {
    private final FilmService filmService;

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
        log.info("Добавление нового фильма.");
        return filmService.createFilm(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film newFilm) {
        log.info("Обновление фильма.");
        return filmService.updateFilm(newFilm);
    }

    @GetMapping
    public Collection<Film> findAllFilm() {
        log.info("Вывод список фильмов.");
        return filmService.findAllFilm();
    }

    @PutMapping(value = "/{id}/like/{userId}")
    public Film addLike(@NotNull @PathVariable long id,
                        @NotNull @PathVariable long userId) {
        return filmService.addLike(id, userId);
    }

    @DeleteMapping(value = "/{id}/like/{userId}")
    public Film deleteLike(@NotNull @PathVariable long id,
                           @NotNull @PathVariable long userId) {
        return filmService.deleteLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms(
            @RequestParam(required = false, defaultValue = "10") int count) {
        return filmService.getPopular(count);
    }
}