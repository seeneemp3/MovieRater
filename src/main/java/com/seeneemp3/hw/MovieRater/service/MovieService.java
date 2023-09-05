package com.seeneemp3.hw.MovieRater.service;

import com.seeneemp3.hw.MovieRater.exception.MovieValidationException;
import com.seeneemp3.hw.MovieRater.model.Movie;
import com.seeneemp3.hw.MovieRater.storage.MovieStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class MovieService {
    private MovieStorage movieStorage;
    @Autowired
    public MovieService(MovieStorage movieStorage){
        this.movieStorage = movieStorage;
    }
    public Long addLike(Long movieId, Long userId) throws MovieValidationException {
        movieStorage.getById(movieId).getLikes().add(userId);
        return userId;
    }
    public Long removeLike(Long movieId, Long userId) throws MovieValidationException {
        movieStorage.getById(movieId).getLikes().remove(userId);
        return userId;
    }
    public List<Movie> mostLiked(Long size){
        return movieStorage.getAll().stream().sorted((Comparator.comparing((Movie m) -> m.getLikes().size()
        ).reversed())).limit(size).toList();
    }
}
