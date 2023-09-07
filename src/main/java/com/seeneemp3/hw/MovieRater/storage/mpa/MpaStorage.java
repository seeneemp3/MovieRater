package com.seeneemp3.hw.MovieRater.storage.mpa;

import com.seeneemp3.hw.MovieRater.exception.MpaNotFoundException;
import com.seeneemp3.hw.MovieRater.exception.ValidationException;
import com.seeneemp3.hw.MovieRater.model.Mpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MpaStorage {
    private final JdbcTemplate jdbcTemplate;

    private final BeanPropertyRowMapper<Mpa> mpaMapper = new BeanPropertyRowMapper<>(Mpa.class);
    @Autowired
    public MpaStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Mpa> getAll() {
        return jdbcTemplate.query("SELECT * FROM ratings_mpa", mpaMapper);
    }

    public Mpa getById(Integer id){
        validate(id);
       return jdbcTemplate.query("SELECT * FROM ratings_mpa WHERE id = ?", mpaMapper, id)
                .stream()
                .findAny()
                .orElseThrow(() -> new MpaNotFoundException("Rating with ID=" + id + " not found!"));
    }




    private <T extends Number> void validate(T id){
        if (id == null) {
            throw new ValidationException("Empty argument passed!");
        }
    }
}
