package com.seeneemp3.hw.MovieRater.service;

import com.seeneemp3.hw.MovieRater.exception.MovieNotFoundException;
import com.seeneemp3.hw.MovieRater.exception.MovieValidationException;
import com.seeneemp3.hw.MovieRater.exception.UserNotFoundException;
import com.seeneemp3.hw.MovieRater.model.Movie;
import com.seeneemp3.hw.MovieRater.storage.likes.LikeStorage;
import com.seeneemp3.hw.MovieRater.storage.movie.MovieStorage;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class MovieService {
    private MovieStorage movieStorage;
    private LikeStorage likeStorage;
    @Autowired
    public MovieService(@Qualifier("movieDbStorage")MovieStorage movieStorage, LikeStorage likeStorage){
        this.likeStorage = likeStorage;
        this.movieStorage = movieStorage;
    }
    public Long addLike(Long movieId, Long userId) {
        likeStorage.addLike(movieId, userId);
        return userId;
    }
    public Long removeLike(Long movieId, Long userId) {
        Movie m = movieStorage.getById(movieId);
        if(m != null){
            if (m.getLikes().contains(userId)){
                likeStorage.deleteLike(movieId, userId);
            }else throw new UserNotFoundException("Лайк от пользователя c ID=" + userId + " не найден!");
        }else throw new MovieNotFoundException("Фильм c ID=" + movieId + " не найден!");
        return userId;
    }
    public List<Long> mostLiked(Integer size){
        return likeStorage.getPopular(size);
    }
}
