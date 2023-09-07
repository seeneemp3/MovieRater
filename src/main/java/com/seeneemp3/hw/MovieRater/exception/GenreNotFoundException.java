package com.seeneemp3.hw.MovieRater.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GenreNotFoundException extends RuntimeException{

    public GenreNotFoundException(String msg) {
        super(msg);
        log.error(msg);
    }
}
