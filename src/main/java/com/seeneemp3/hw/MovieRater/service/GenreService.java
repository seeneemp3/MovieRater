package com.seeneemp3.hw.MovieRater.service;

import com.seeneemp3.hw.MovieRater.model.Genre;
import com.seeneemp3.hw.MovieRater.model.Movie;
import com.seeneemp3.hw.MovieRater.storage.genre.GenreStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GenreService {
    private GenreStorage genreStorage;
    @Autowired
    public GenreService(GenreStorage genreStorage) {
        this.genreStorage = genreStorage;
    }
    public Collection<Genre> getAll() {
        return genreStorage.getAll()
                .stream()
                .sorted(Comparator.comparing(Genre::getId))
                .collect(Collectors.toList());
    }

    public Genre getGenreById(Integer id) {
        return genreStorage.getById(id);
    }

    public void putGenres(Movie m) {
        genreStorage.delete(m);
        genreStorage.add(m);
    }

    public Set<Genre> getFilmGenres(Long filmId) {
        return new HashSet<>(genreStorage.getMovieGenres(filmId));
    }
}
