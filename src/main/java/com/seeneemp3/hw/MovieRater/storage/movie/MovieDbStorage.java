package com.seeneemp3.hw.MovieRater.storage.movie;

import com.seeneemp3.hw.MovieRater.exception.MovieNotFoundException;
import com.seeneemp3.hw.MovieRater.exception.UserNotFoundException;
import com.seeneemp3.hw.MovieRater.exception.ValidationException;
import com.seeneemp3.hw.MovieRater.model.Genre;
import com.seeneemp3.hw.MovieRater.model.Movie;
import com.seeneemp3.hw.MovieRater.service.GenreService;
import com.seeneemp3.hw.MovieRater.service.MpaService;
import com.seeneemp3.hw.MovieRater.storage.likes.LikeStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
//TODO: notnull arguments validation

@Component("movieDbStorage")
@Slf4j
public class MovieDbStorage implements MovieStorage {
    private final JdbcTemplate jdbcTemplate;
    private final MpaService mpaService;
    private final GenreService genreService;
    private final LikeStorage likeStorage;
    private final MovieRowMapper movieMapper;

    @Autowired
    public MovieDbStorage(JdbcTemplate jdbcTemplate, MpaService mpaService, GenreService genreService,
                          LikeStorage likeStorage, MovieRowMapper movieMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.mpaService = mpaService;
        this.genreService = genreService;
        this.likeStorage = likeStorage;
        this.movieMapper = movieMapper;
    }

    @Override
    public List<Movie> getAll() {
        return jdbcTemplate.query("SELECT * FROM movies", movieMapper);
    }

    @Override
    public Movie create(Movie movie) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("movies")
                .usingGeneratedKeyColumns("id");
        Long newId = simpleJdbcInsert.executeAndReturnKey(movie.toMap()).longValue();
        movie.setId(newId);
        movie.setMpa(mpaService.getById(movie.getMpa().getId()));
        if (movie.getGenres() != null) {
            for (Genre genre : movie.getGenres()) {
                genre.setName(genreService.getById(genre.getId()).getName());
            }
            genreService.putGenres(movie);
        }
        log.error(movie.toString());
        return movie;
    }

    @Override
    public Movie update(Movie movie) {
        if (movie == null) {
            throw new ValidationException("Передан пустой аргумент!");
        }
        String sqlQuery = """
                UPDATE films
                SET name = ?, description = ?, release_date = ?, duration = ?, rating_id = ?
                WHERE id = ?"
                """;
        if (jdbcTemplate.update(sqlQuery,
                movie.getName(),
                movie.getDescription(),
                movie.getReleaseDate(),
                movie.getDuration(),
                movie.getMpa().getId(),
                movie.getId()) != 0) {
            movie.setMpa(mpaService.getById(movie.getMpa().getId()));
            if (movie.getGenres() != null) {
                Collection<Genre> sortGenres = movie.getGenres().stream()
                        .sorted(Comparator.comparing(Genre::getId))
                        .toList();
                movie.setGenres(new LinkedHashSet<>(sortGenres));
                for (Genre genre : movie.getGenres()) {
                    genre.setName(genreService.getById(genre.getId()).getName());
                }
            }
            genreService.putGenres(movie);
            return movie;
        } else {
            throw new MovieNotFoundException("Фильм с ID=" + movie.getId() + " не найден!");
        }
    }


    @Override
    public Movie getById(Long movieId) {
        return jdbcTemplate.query("SELECT * FROM movies WHERE id = ?", movieMapper, movieId)
                .stream()
                .findAny()
                .orElseThrow(() -> new MovieNotFoundException("Фильм с ID=" + movieId + " не найден!"));
    }

    @Override
    public Movie delete(Long movieId) {
        Movie movie = getById(movieId);
        int res = jdbcTemplate.update("DELETE FROM movies WHERE id = ?", movieId);
        if (res == 0) {
            throw new UserNotFoundException("Фильм с ID=" + movieId + " не найден!");
        }
        log.info("Фильм с ID={} успешно удален", movie.getId());
        return movie;
    }
}
