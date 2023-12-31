package com.seeneemp3.hw.MovieRater.controller;

import com.seeneemp3.hw.MovieRater.model.Movie;
import com.seeneemp3.hw.MovieRater.service.MovieService;
import com.seeneemp3.hw.MovieRater.storage.movie.MovieStorage;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/movies")
public class MovieController {
    private final MovieStorage movieStorage;
    private final MovieService movieService;

    @Autowired
    public MovieController(@Qualifier("movieDbStorage") MovieStorage movieStorage, MovieService movieService) {
        this.movieService = movieService;
        this.movieStorage = movieStorage;
    }

    @GetMapping
    public List<Movie> getAll() {
        log.info("Received a GET request to the endpoint: '/movies' to retrieve all movies.");
        return movieStorage.getAll();
    }

    @GetMapping("/{id}")
    public Movie getById(@PathVariable Long id) {
        log.info("Received a GET request to the endpoint: '/movies' to retrieve a movie with ID={}", id);
        return movieStorage.getById(id);
    }

    @GetMapping("/popular")
    public List<Long> getTopList(@RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Received a GET request to the endpoint: '/movies' to retrieve a list of popular movies");
        return movieService.mostLiked(size);
    }

    @PostMapping
    public Movie create(@Valid @RequestBody Movie movie) {
        log.info("Received a POST request to the endpoint: '/movies' for adding a movie");
        return movieStorage.create(movie);
    }

    @PutMapping
    public Movie update(@Valid @RequestBody Movie movie) {
        log.info("Received a PUT request to the endpoint: '/movies' for updating a movie with ID={}", movie.getId());
        return movieStorage.update(movie);
    }

    @DeleteMapping("/{id}")
    public Movie delete(@PathVariable Long id) {
        log.info("Received a DELETE request to the endpoint: '/movies' for deleting a movie with ID={}", id);
        return movieStorage.delete(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public Long addLike(@PathVariable Long id, @PathVariable Long userId) {
        log.info("Received a PUT request for adding a like");
        return movieService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Long removeLike(@PathVariable Long id, @PathVariable Long userId) {
        log.info("Received a DELETE request for removing a like");
        return movieService.removeLike(id, userId);
    }
}
