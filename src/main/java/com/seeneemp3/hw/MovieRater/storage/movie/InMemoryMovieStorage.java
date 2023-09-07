package com.seeneemp3.hw.MovieRater.storage.movie;

import com.seeneemp3.hw.MovieRater.exception.MovieValidationException;
import com.seeneemp3.hw.MovieRater.model.Movie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Component
public class InMemoryMovieStorage implements MovieStorage {
    private final HashMap<Long, Movie> movies;
    private Long currentId;

    public InMemoryMovieStorage() {
        currentId = 0L;
        movies = new HashMap<>();
    }

    public List<Movie> getAll() {
        return new ArrayList<>(movies.values());
    }

    @Override
    public Movie create(Movie movie) throws MovieValidationException {
        if (movies.containsValue(movie)) {
            throw new MovieValidationException("Movie already exists");
        }
        movie.setId(++currentId);
        movies.put(movie.getId(), movie);
        return movie;
    }

    @Override
    public Movie update(Movie movie) throws MovieValidationException {
        if (movie.getId() == null) {
            throw new MovieValidationException("Wrong argument: id");
        }
        if (!movies.containsKey(movie.getId())) {
            throw new MovieValidationException("No movie with such id");
        }
        movies.put(movie.getId(), movie);
        return movie;
    }

    @Override
    public Movie getById(Long movieId) throws MovieValidationException {
        if (!movies.containsKey(movieId)) {
            throw new MovieValidationException("No movie with such id");
        }
        return movies.get(movieId);
    }

    @Override
    public Movie delete(Long movieId) throws MovieValidationException {
        if (movieId == null) {
            throw new MovieValidationException("Wrong argument: id");
        }
        if (!movies.containsKey(movieId)) {
            throw new MovieValidationException("No movie with such id");
        }
        return movies.remove(movieId);
    }
}
