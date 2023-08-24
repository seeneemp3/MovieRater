package com.seeneemp3.hw.MovieRater.exception;


public class UserAlreadyExistException extends Exception{
    private String msg;
    public UserAlreadyExistException(String msg){
        this.msg = msg;
    }
}