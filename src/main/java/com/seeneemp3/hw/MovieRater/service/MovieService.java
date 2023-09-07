package com.seeneemp3.hw.MovieRater.service;

import com.seeneemp3.hw.MovieRater.exception.MovieNotFoundException;
import com.seeneemp3.hw.MovieRater.exception.UserNotFoundException;
import com.seeneemp3.hw.MovieRater.model.Movie;
import com.seeneemp3.hw.MovieRater.storage.likes.LikeStorage;
import com.seeneemp3.hw.MovieRater.storage.movie.MovieStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    private final MovieStorage movieStorage;
    private final LikeStorage likeStorage;

    @Autowired
    public MovieService(@Qualifier("movieDbStorage") MovieStorage movieStorage, LikeStorage likeStorage) {
        this.likeStorage = likeStorage;
        this.movieStorage = movieStorage;
    }

    public Long addLike(Long movieId, Long userId) {
        likeStorage.addLike(movieId, userId);
        return userId;
    }

    public Long removeLike(Long movieId, Long userId) {
        Movie m = movieStorage.getById(movieId);
        if (m != null) {
            if (m.getLikes().contains(userId)) {
                likeStorage.deleteLike(movieId, userId);
            } else throw new UserNotFoundException("Like from user with ID=" + userId + " not found!");
        } else throw new MovieNotFoundException("Movie with ID=" + movieId + " not found!");
        return userId;
    }

    public List<Long> mostLiked(Integer size) {
        return likeStorage.getPopular(size);
    }
}
