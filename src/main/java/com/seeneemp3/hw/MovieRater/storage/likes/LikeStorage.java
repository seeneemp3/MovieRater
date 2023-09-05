package com.seeneemp3.hw.MovieRater.storage.likes;

import com.seeneemp3.hw.MovieRater.model.Movie;
import com.seeneemp3.hw.MovieRater.service.GenreService;
import com.seeneemp3.hw.MovieRater.service.MpaService;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LikeStorage {
    private final JdbcTemplate jdbcTemplate;
    private MpaService mpaService;
    private GenreService genreService;
    private BeanPropertyRowMapper<Movie> movieMapper = new BeanPropertyRowMapper<>(Movie.class);


    @Autowired
    public LikeStorage(JdbcTemplate jdbcTemplate, MpaService mpaService, GenreService genreService) {
        this.jdbcTemplate = jdbcTemplate;
        this.mpaService = mpaService;
        this.genreService = genreService;
    }

    public void addLike(Long movieId, Long userId) {
        String sql = "INSERT INTO movie_likes (movie_id, user_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, movieId, userId);
    }

    public void deleteLike(Long movieId, Long userId) {
        String sql = "DELETE FROM movie_likes WHERE movie_id = ? AND user_id = ?";
        jdbcTemplate.update(sql, movieId, userId);
    }

    public List<Movie> getPopular(@Positive Integer count){
        String sql = """
                SELECT * FROM movies
                JOIN movie_likes l
                ON movie_id = l.movie_id
                GROUP BY movie_id
                ORDER BY COUNT(l.user_id) DESC
                LIMIT ?
                """;
       return jdbcTemplate.query(sql, movieMapper, count);
    }

    public List<Long> getLikes(Long movieId){
        String sql = "SELECT user_id FROM movie_likes WHERE movie_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("user_id"), movieId);
    }

}
