package com.seeneemp3.hw.MovieRater.storage.movie;

import com.seeneemp3.hw.MovieRater.exception.MovieValidationException;
import com.seeneemp3.hw.MovieRater.model.Movie;

import java.util.List;

public interface MovieStorage {
    List<Movie> getAll();
    Movie create(Movie movie);
    Movie update(Movie movie);
    Movie getById(Long movieId);
    Movie delete(Long movieId);
}
