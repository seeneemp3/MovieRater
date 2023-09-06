package com.seeneemp3.hw.MovieRater.storage.movie;

import com.seeneemp3.hw.MovieRater.model.Movie;
import com.seeneemp3.hw.MovieRater.service.GenreService;
import com.seeneemp3.hw.MovieRater.service.MpaService;
import com.seeneemp3.hw.MovieRater.storage.likes.LikeStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
@Component
public class MovieRowMapper implements RowMapper<Movie> {
    LikeStorage likeStorage;
    MpaService mpaService;
    GenreService genreService;
    @Autowired
    public MovieRowMapper(LikeStorage likeStorage, MpaService mpaService, GenreService genreService) {
        this.likeStorage = likeStorage;
        this.mpaService = mpaService;
        this.genreService = genreService;
    }

    public Movie mapRow(ResultSet res, int rowNum) throws SQLException{
        Movie m = new Movie();
        m.setId(res.getLong("id"));
        m.setName(res.getString("name"));
        m.setDuration(res.getLong("duration"));
        m.setDescription(res.getString("description"));
        m.setReleaseDate(res.getDate("release_Date").toLocalDate());
        m.setLikes(new HashSet<>(likeStorage.getLikes(res.getLong("id"))));
        m.setMpa(mpaService.getById(res.getInt("rating_id")));
        m.setGenres(genreService.getMovieGenres(res.getLong("id")));
        return m;
    }
}
