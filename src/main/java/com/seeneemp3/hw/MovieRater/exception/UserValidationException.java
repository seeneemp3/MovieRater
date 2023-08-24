package com.seeneemp3.hw.MovieRater.exception;

public class UserValidationException extends Exception{
    private String msg;
    public UserValidationException(String msg){
        this.msg = msg;
    }
}
