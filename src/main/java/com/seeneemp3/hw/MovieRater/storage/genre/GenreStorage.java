package com.seeneemp3.hw.MovieRater.storage.genre;

import com.seeneemp3.hw.MovieRater.exception.GenreNotFoundException;
import com.seeneemp3.hw.MovieRater.exception.ValidationException;
import com.seeneemp3.hw.MovieRater.model.Genre;
import com.seeneemp3.hw.MovieRater.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GenreStorage {
    private final JdbcTemplate jdbcTemplate;
    private final BeanPropertyRowMapper<Genre> genreMapper = new BeanPropertyRowMapper<>(Genre.class);

    @Autowired
    public GenreStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Genre> getAll() {
        return jdbcTemplate.query("SELECT * FROM genres", genreMapper);
    }

    public Genre getById(Integer id) {
        validate(id);
        return jdbcTemplate.query("SELECT * FROM genres WHERE id = ?", genreMapper, id)
                .stream().findAny()
                .orElseThrow(() -> new GenreNotFoundException("Жанр с ID=" + id + " не найден!"));
    }

    public void delete(Movie m) {
        jdbcTemplate.update("DELETE FROM movie_genres WHERE movie_id = ?", m.getId());
    }

    public void add(Movie m) {
        if (m.getGenres() != null) {
            for (Genre genre : m.getGenres()) {
                jdbcTemplate.update("INSERT INTO movie_genres (movie_id, genre_id) VALUES (?, ?)",
                        m.getId(), genre.getId());
            }
        }
    }

    public List<Genre> getMovieGenres(Long movieId) {
        validate(movieId);
        String sql = "SELECT genre_id, name FROM movie_genres" +
                " INNER JOIN genres ON genre_id = id WHERE movie_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Genre(
                rs.getInt("genre_id"), rs.getString("name")), movieId);
    }

    private <T extends Number> void validate(T id) {
        if (id == null) {
            throw new ValidationException("Empty argument passed!");
        }
    }

}
