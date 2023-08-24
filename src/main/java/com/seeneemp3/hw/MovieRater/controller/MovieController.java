package com.seeneemp3.hw.MovieRater.controller;

import com.seeneemp3.hw.MovieRater.exception.MovieValidationException;
import com.seeneemp3.hw.MovieRater.model.Movie;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collection;
import java.util.HashMap;
@Slf4j
@RestController
public class MovieController {
    private HashMap<Integer, Movie> movies = new HashMap<>();
    @GetMapping("/movie")
    public Collection<Movie> get(){
        log.debug("Movies count: {}", movies.size());
        return movies.values();
    }

    @PostMapping("/movie")
    public ResponseEntity<String> post(@Valid @RequestBody Movie movie) throws MovieValidationException {

            //log.error("Ошибка при добавлении фильма {}. Проверьте правильность введенных данных.", movie);
            //return ResponseEntity.badRequest().body("Ошибка при добавлении фильма. Проверьте правильность введенных данных.");
            //throw new MovieValidationException("Ошибка при добавлении фильма. Проверьте правильность введенных данных.");

        if(movies.containsValue(movie)) {
            log.error("Ошибка при добавлении фильма {}. Такой фильм уже существует.", movie);
            //throw new MovieValidationException("Ошибка при добавлении фильма. Такой фильм уже существует.");
            return ResponseEntity.badRequest().body("Ошибка при добавлении фильма. Такой фильм уже существует.");
        }
        log.debug("Movie has been added: {}", movie);
        movies.put(movie.getId(), movie);
        return ResponseEntity.ok(movie.toString());
    }

    @PutMapping("/movie")
    public Movie put(@Valid @RequestBody Movie movie) throws MovieValidationException {
        if(
        movie.getName().isBlank()
        || movie.getDescription().length() > 200
        || movie.getReleaseDate().isBefore(LocalDate.of(1895, Month.DECEMBER, 28))
        || movie.getDuration() < 0
        ) throw new MovieValidationException("Ошибка при добавлении фильма. Проверьте правильность введенных данных.");
        log.debug("Movie has been updated: {}", movie);
        movies.put(movie.getId(), movie);
        return movie;
    }
}
