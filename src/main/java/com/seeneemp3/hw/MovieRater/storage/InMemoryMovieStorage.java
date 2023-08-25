package com.seeneemp3.hw.MovieRater.storage;

import com.seeneemp3.hw.MovieRater.exception.MovieValidationException;
import com.seeneemp3.hw.MovieRater.model.Movie;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Component
public class InMemoryMovieStorage implements MovieStorage {
    private HashMap<Long, Movie> movies = new HashMap<>();
    private Long currentId;

    public InMemoryMovieStorage(){
        currentId = 0L;
        movies = new HashMap<>();
    }

    public List<Movie> getAll(){
        return new ArrayList<>(movies.values());
    }

    @Override
    public Movie create(Movie movie) throws MovieValidationException {
        movie.setId(++currentId);
        movies.put(movie.getId(), movie);
//        if(movies.containsValue(movie)){
//            log.error("Ошибка при добавлении фильма {}. Такой фильм уже существует.", movie);
//            throw new MovieValidationException("Ошибка при добавлении фильма. Такой фильм уже существует.");
//        }
//        log.debug("Movie has been added: {}", movie);
        return movie;
    }

    @Override
    public Movie update(Movie movie) throws MovieValidationException {
        if(movie.getId() == null ){
            throw new ValidationException("Wrong argument: id");
        }
        if(!movies.containsKey(movie.getId())){
            throw new ValidationException("No movie with this id");
        }
        // if valid ...
        movies.put(movie.getId(), movie);

//        if(movies.containsValue(movie)){
//            log.error("Ошибка при добавлении фильма {}. Такой фильм уже существует.", movie);
//            throw new MovieValidationException("Ошибка при добавлении фильма. Такой фильм уже существует.");
//        }
//        log.debug("Movie has been added: {}", movie);

        return movie;
    }

    @Override
    public Movie getById(Long movieId) {
        if(!movies.containsKey(movieId)){
            throw new ValidationException("No movie with this id");
        }
        return movies.get(movieId);
    }

    @Override
    public Movie delete(Long movieId) {
        if(movieId == null ){
            throw new ValidationException("Wrong argument: id");
        }
        if(!movies.containsKey(movieId)){
            throw new ValidationException("No movie with this id");
        }
        return movies.remove(movieId);
    }
}
