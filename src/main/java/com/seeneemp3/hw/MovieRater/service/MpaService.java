package com.seeneemp3.hw.MovieRater.service;

import com.seeneemp3.hw.MovieRater.model.Mpa;
import com.seeneemp3.hw.MovieRater.storage.mpa.MpaStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
public class MpaService {

    private MpaStorage mpaStorage;

    @Autowired
    public MpaService(MpaStorage mpaStorage) {
        this.mpaStorage = mpaStorage;
    }

    public Collection<Mpa> getAllMpa() {
        return mpaStorage.getAll().stream()
                .sorted(Comparator.comparing(Mpa::getId))
                .collect(Collectors.toList());
    }

    public Mpa getById(Integer id) {
        return mpaStorage.getById(id);
    }



}
