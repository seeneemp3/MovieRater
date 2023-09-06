package com.seeneemp3.hw.MovieRater.controller;

import com.seeneemp3.hw.MovieRater.exception.MovieValidationException;
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
    private MovieStorage movieStorage;
    private MovieService movieService;
    @Autowired
    public MovieController(@Qualifier("movieDbStorage")MovieStorage movieStorage, MovieService movieService){
        this.movieService = movieService;
        this.movieStorage = movieStorage;
    }

    @GetMapping
    public List<Movie> getAll(){
        return movieStorage.getAll();
    }

    @GetMapping("/{id}")
    public Movie getById(@PathVariable Long id) {
        return movieStorage.getById(id);
    }
    @GetMapping("/popular")
    public List<Movie> getTopList(@RequestParam(name = "size", defaultValue = "10") Integer size) {
        return movieService.mostLiked(size);
    }

    @PostMapping
    public Movie create(@Valid @RequestBody Movie movie){
        //////////////////////////////////
        return movieStorage.create(movie);
    }

    @PutMapping
    public Movie update(@Valid @RequestBody Movie movie){
        //////////////////////////////////
        return movieStorage.update(movie);
    }
    @DeleteMapping("/{id}")
    public Movie delete(@PathVariable Long id){
        return movieStorage.delete(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public Long addLike(@PathVariable Long id, @PathVariable Long userId ) {
        return movieService.addLike(id, userId);
    }
    @DeleteMapping("/{id}/like/{userId}")
    public Long removeLike(@PathVariable Long id, @PathVariable Long userId ) {
        return movieService.removeLike(id, userId);
    }
}
