package com.seeneemp3.hw.MovieRater.controller;

import com.seeneemp3.hw.MovieRater.model.Mpa;
import com.seeneemp3.hw.MovieRater.service.MpaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/mpa")
@Slf4j
public class MpaController {

    private MpaService mpaService;

    @Autowired
    public MpaController(MpaService mpaService) {
        this.mpaService = mpaService;
    }

    @GetMapping
    public Collection<Mpa> getAll() {
        log.info("Получен GET-запрос к эндпоинту: '/mpa' на получение всех рейтингов");
        return mpaService.getAll();
    }

    @GetMapping("/{id}")
    public Mpa getById(@PathVariable Integer id) {
        log.info("Получен GET-запрос к эндпоинту: '/mpa' на получение рейтинга с ID={}", id);
        return mpaService.getById(id);
    }
}
