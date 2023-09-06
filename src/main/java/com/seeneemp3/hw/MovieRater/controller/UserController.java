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
    //TODO: разделить статус дружбы
    //TODO: сделать проверку на добавление дубликата
    //TODO: прочтитать про @Qualifier
    //TODO: посмотреть хорошую реализацию CRUD
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
    public User post(@Valid @RequestBody User user) throws UserAlreadyExistException {
        return userStorage.create(user);
    }

    @PutMapping
    public User put(@Valid @RequestBody User user) {
       return userStorage.update(user);
    }
    @DeleteMapping("/{id}")
    public User deleteUser(@PathVariable Long id){
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
    public Set<User> getFriends(@PathVariable Long id) {
        return userService.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{friendId}")
    public Set<User> getCommon(@PathVariable Long id, @PathVariable Long friendId){
        return userService.commonFriends(id,friendId);
    }
}
