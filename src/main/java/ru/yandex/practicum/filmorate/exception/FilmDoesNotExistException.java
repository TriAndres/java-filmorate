package ru.yandex.practicum.filmorate.exception;

public class FilmDoesNotExistException extends RuntimeException {
    public FilmDoesNotExistException(String message) {
        super(message);
    }
}
