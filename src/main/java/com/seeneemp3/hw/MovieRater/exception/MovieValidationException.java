package com.seeneemp3.hw.MovieRater.exception;

public class MovieValidationException extends Exception{
    private String msg;
    public MovieValidationException(String msg){
        this.msg = msg;
    }
}
