package com.seeneemp3.hw.MovieRater.controller;

import com.seeneemp3.hw.MovieRater.exception.UserAlreadyExistException;
import com.seeneemp3.hw.MovieRater.exception.UserValidationException;
import com.seeneemp3.hw.MovieRater.model.User;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@RestController
public class UserController {
    private HashMap<Integer, User> users = new HashMap<>();
    @GetMapping("/user")
    public Collection <User> get(){
        log.debug("Users count: {}", users.size());
        return users.values();
    }

    @PostMapping("/user")
    public User post(@Valid @RequestBody User user) throws UserAlreadyExistException, UserValidationException {
//        if(!user.getEmail().matches("^\\S+@\\S+\\.\\S+$") || user.getEmail().isBlank()) throw new InvalidEmailException("Проверьте правильновть введенного email.");
        if(user.getBirthday().isAfter(LocalDate.now())) throw new UserValidationException("Проверьте правильновть введенных данных.");
        if(user.getName().isBlank()) user.setName(user.getLogin());
        if(users.containsKey(user.getId())) throw new UserAlreadyExistException("Пользователь с электронной почтой " + user.getEmail() + " уже зарегистрирован.");

        log.debug("User has been added: {}", user);
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User put(@Valid @RequestBody User user) throws UserValidationException {
        if(user.getBirthday().isAfter(LocalDate.now())) throw new UserValidationException("Проверьте правильновть введенных данных.");
        if(user.getName().isBlank()) user.setName(user.getLogin());

        log.debug("User has been updated: {}", user);
        users.put(user.getId(), user);
        return user;
    }
}
