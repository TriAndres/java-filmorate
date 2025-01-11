package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Film {
    private Long id;
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @NotBlank
    @Size(max = 200)
    private String description;
    @NotNull
    //@ReleaseDateValidation  дата релиза — не раньше 28 декабря 1895 года;
    private LocalDate releaseDate;
    @NotNull
    @Positive
    private Integer duration;
}
