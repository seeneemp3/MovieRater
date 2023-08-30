package com.seeneemp3.hw.MovieRater.storage;

import com.seeneemp3.hw.MovieRater.exception.MovieValidationException;
import com.seeneemp3.hw.MovieRater.model.Movie;

import java.util.List;

public interface MovieStorage {
    List<Movie> getAll();
    Movie create(Movie movie) throws MovieValidationException;
    Movie update(Movie movie) throws MovieValidationException;
    Movie getById(Long movieId) throws MovieValidationException;
    Movie delete(Long movieId) throws MovieValidationException;
}
