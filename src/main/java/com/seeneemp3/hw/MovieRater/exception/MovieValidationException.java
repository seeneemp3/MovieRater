package com.seeneemp3.hw.MovieRater.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MovieValidationException extends Exception{
    private String msg;
    public MovieValidationException(String msg){
        super(msg);
    }
}
