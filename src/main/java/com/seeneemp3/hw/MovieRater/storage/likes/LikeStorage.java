package com.seeneemp3.hw.MovieRater.storage.likes;

import com.seeneemp3.hw.MovieRater.service.GenreService;
import com.seeneemp3.hw.MovieRater.service.MpaService;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LikeStorage {
    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public LikeStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addLike(Long movieId, Long userId) {
        String sql = "INSERT INTO movie_likes (movie_id, user_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, movieId, userId);
    }

    public void deleteLike(Long movieId, Long userId) {
        String sql = "DELETE FROM movie_likes WHERE movie_id = ? AND user_id = ?";
        jdbcTemplate.update(sql, movieId, userId);
    }

    public List<Long> getPopular(@Positive Integer count) {
        String sql = """
                SELECT id FROM movies
                JOIN movie_likes AS l
                ON movie_id = l.movie_id
                GROUP BY movies.id
                ORDER BY COUNT(l.user_id) DESC
                LIMIT ?
                """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("id"), count);
    }

    public List<Long> getLikes(Long movieId) {
        String sql = "SELECT user_id FROM movie_likes WHERE movie_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("user_id"), movieId);
    }

}
