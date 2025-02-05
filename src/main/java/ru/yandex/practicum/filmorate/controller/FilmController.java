package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
@Slf4j
public class FilmController {

    private final FilmService filmService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Film createFilm(@Valid @RequestBody Film film) {
        log.info("createFilm(film)");
        return filmService.createFilm(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film newFilm) {
        log.error("updateFilm(newFilm)");
        return filmService.updateFilm(newFilm);
    }

    @GetMapping
    public Collection<Film> findAllFilm() {
        log.info("findAllFilm()");
        return filmService.findAllFilm();
    }

    @GetMapping("/{filmId}")
    public Optional<Film> findByIdFilm(@PathVariable long filmId) {
        log.info("getByIdFilm(filmId)");
        return filmService.findByIdFilm(filmId);
    }

    @DeleteMapping("/id")
    public void deleteByIdFilm(@Valid @PathVariable long id) {
        log.info("deleteByIdFilm(id)");
        filmService.deleteById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public Film addLike(@NotNull @PathVariable long id,
                        @NotNull @PathVariable long userId) {
        log.info("addLike(id, userId)");
        return filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film deleteLike(@NotNull @PathVariable long id,
                           @NotNull @PathVariable long userId) {
        log.info("deleteLike(id, userId)");
        return filmService.deleteLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms(
            @RequestParam(required = false, defaultValue = "10") int count) {
        log.info("getPopularFilms(popular?count={count})");
        return filmService.getPopularFilms(count);
    }
}