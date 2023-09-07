package com.seeneemp3.hw.MovieRater.controller;

import com.seeneemp3.hw.MovieRater.exception.UserAlreadyExistException;
import com.seeneemp3.hw.MovieRater.model.User;
import com.seeneemp3.hw.MovieRater.service.UserService;
import com.seeneemp3.hw.MovieRater.storage.user.UserStorage;
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
    private final UserStorage userStorage;
    private final UserService userService;

    @Autowired
    public UserController(UserStorage userStorage, UserService userService) {
        this.userStorage = userStorage;
        this.userService = userService;
    }

    @GetMapping
    public List<User> get() {
        log.info("Received a GET request to retrieve all users.");
        return userStorage.getAll();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        log.info("Received a GET request to retrieve a user with ID: {}", id);
        return userStorage.getById(id);
    }

    @PostMapping
    public User post(@Valid @RequestBody User user){
        log.info("Received a POST request to create a new user: {}", user);
        return userStorage.create(user);
    }

    @PutMapping
    public User put(@Valid @RequestBody User user) {
        log.info("Received a PUT request to update user with ID: {}", user.getId());
        return userStorage.update(user);
    }

    @DeleteMapping("/{id}")
    public User deleteUser(@PathVariable Long id) {
        log.info("Received a DELETE request to delete user with ID: {}", id);
        return userStorage.delete(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public Long addFriend(@PathVariable Long id, @PathVariable Long friendId) {
        log.info("Received a PUT request to add friend with ID {} to user with ID: {}", friendId, id);
        return userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public Long deleteFriend(@PathVariable Long id, @PathVariable Long friendId) {
        log.info("Received a DELETE request to remove friend with ID {} from user with ID: {}", friendId, id);
        return userService.deleteFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public Set<Long> getFriends(@PathVariable Long id) {
        log.info("Received a GET request to retrieve friends for user with ID: {}", id);
        return userService.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{friendId}")
    public Set<Long> getCommon(@PathVariable Long id, @PathVariable Long friendId) {
        log.info("Received a GET request to retrieve common friends between user with ID {} and user with ID {}", id, friendId);
        return userService.commonFriends(id, friendId);
    }
}
