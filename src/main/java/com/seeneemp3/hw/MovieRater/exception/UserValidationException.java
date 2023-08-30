package com.seeneemp3.hw.MovieRater.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserValidationException extends Exception{
    private String msg;
    public UserValidationException(String msg){
        super(msg);
        log.error(msg);
    }
}
