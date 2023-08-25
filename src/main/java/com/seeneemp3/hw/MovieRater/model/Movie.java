package com.seeneemp3.hw.MovieRater.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Data
public class Movie {

    private Long id;
    @NotBlank
    @NonNull
    private String name;
    @Size(max = 200)
    private String description;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate releaseDate;
    @Positive
    private long duration;
    private Set<Long> likes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return duration == movie.duration && name.equals(movie.name) && description.equals(movie.description) && releaseDate.equals(movie.releaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, releaseDate, duration);
    }
}
