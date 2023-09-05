package com.seeneemp3.hw.MovieRater.storage.movie;

import com.seeneemp3.hw.MovieRater.model.Movie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@Primary
public class MovieDbStorage implements MovieStorage {
    private final JdbcTemplate jdbcTemplate;
    private final BeanPropertyRowMapper<Movie> movieMapper = new BeanPropertyRowMapper<>(Movie.class);

    @Autowired
    public MovieDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Movie> getAll() {
        return jdbcTemplate.query("SELECT * FROM movies", movieMapper);
    }


    @Override
    public Movie create(Movie movie) {

        return null;
    }

    @Override
    public Movie update(Movie movie)  {
        return null;
    }

    @Override
    public Movie getById(Long movieId) {
        return null;
    }

    @Override
    public Movie delete(Long movieId)  {
        return null;
    }
}
