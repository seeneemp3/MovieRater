package com.seeneemp3.hw.MovieRater.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MovieNotFoundException extends RuntimeException{
    private String msg;

    public MovieNotFoundException(String msg) {
        super(msg);
        log.error(msg);
    }
}
