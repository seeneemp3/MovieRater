package com.seeneemp3.hw.MovieRater.controller;

import com.seeneemp3.hw.MovieRater.exception.MovieNotFoundException;
import com.seeneemp3.hw.MovieRater.exception.UserNotFoundException;
import com.seeneemp3.hw.MovieRater.model.ErrorResponse;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserNotFoundException(final UserNotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }
//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ErrorResponse handleMpaNotFoundException(final MpaNotFoundException e) {
//        return new ErrorResponse(e.getMessage());
//    }

//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ErrorResponse handleGenreNotFoundException(final GenreNotFoundException e) {
//        return new ErrorResponse(e.getMessage());
//    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(final ValidationException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleFilmNotFoundException(MovieNotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }
}
