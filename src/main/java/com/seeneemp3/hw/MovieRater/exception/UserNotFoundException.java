package com.seeneemp3.hw.MovieRater.exception;

public class UserNotFoundException extends RuntimeException{
    private String msg;

    public UserNotFoundException(String msg) {
        this.msg = msg;
    }
}
