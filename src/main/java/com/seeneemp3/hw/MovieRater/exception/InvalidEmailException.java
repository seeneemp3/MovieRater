package com.seeneemp3.hw.MovieRater.exception;

public class InvalidEmailException extends Exception{
    private String msg;
    public InvalidEmailException(String msg){
        this.msg = msg;
    }
}
