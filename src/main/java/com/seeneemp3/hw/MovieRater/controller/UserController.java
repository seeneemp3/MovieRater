package com.seeneemp3.hw.MovieRater.controller;

import com.seeneemp3.hw.MovieRater.model.User;
import com.seeneemp3.hw.MovieRater.service.UserService;
import com.seeneemp3.hw.MovieRater.storage.UserStorage;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private UserStorage userStorage;
    private UserService userService;
    @Autowired
    public UserController(UserStorage userStorage, UserService userService){
        this.userStorage = userStorage;
        this.userService = userService;
    }
    @GetMapping
    public List <User> get(){
       return userStorage.getAll();
    }
    @GetMapping("/{id}")
    public User getById(@PathVariable Long id){
        return userStorage.getById(id);
    }

    @PostMapping
    public User post(@Valid @RequestBody User user) {
        return userStorage.create(user);
    }

    @PutMapping
    public User put(@Valid @RequestBody User user) {
       return userStorage.update(user);
    }
    @DeleteMapping("/{id}")
    public User deleteUser(Long id){
       return userStorage.delete(id);
    }
    @PutMapping("/{id}/friends/{friendId}")
    public Long addFriend(@PathVariable Long id, @PathVariable Long friendId){
       return userService.addFriend(id,friendId);
    }
    @DeleteMapping("/{id}/friends/{friendId}")
    public Long deleteFriend(@PathVariable Long id, @PathVariable Long friendId){
        return userService.deleteFriend(id,friendId);
    }
    @GetMapping("/{id}/friends")
    public Set<Long> getFriends(@PathVariable Long id) {
        return userService.getFriends(id);
    }

}
