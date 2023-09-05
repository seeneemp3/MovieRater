package com.seeneemp3.hw.MovieRater.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MpaNotFoundException extends IllegalArgumentException {
    public MpaNotFoundException(String message) {
        super(message);
        log.error(message);
    }
}
