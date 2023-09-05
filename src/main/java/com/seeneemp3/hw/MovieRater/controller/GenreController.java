package com.seeneemp3.hw.MovieRater.controller;

import com.seeneemp3.hw.MovieRater.model.Genre;
import com.seeneemp3.hw.MovieRater.service.GenreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/genres")
@Slf4j
public class GenreController {
    private GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public Collection<Genre> getAll(){
        log.info("Получен GET-запрос к эндпоинту: '/genres' на получение всех жанров");
        return genreService.getAll();
    }
    @GetMapping("/{id}")
    public Genre getById(@PathVariable Integer id){
        log.info("Получен GET-запрос к эндпоинту: '/genres' на получение жанра с ID={}", id);
        return genreService.getGenreById(id);
    }
}
