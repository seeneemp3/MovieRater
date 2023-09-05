package com.seeneemp3.hw.MovieRater.service;

import com.seeneemp3.hw.MovieRater.exception.UserNotFoundException;
import com.seeneemp3.hw.MovieRater.model.User;
import com.seeneemp3.hw.MovieRater.storage.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage){
        this.userStorage = userStorage;
    }

    public Long addFriend(Long userId, Long friendId) {
        validate(userId,friendId);
        userStorage.getById(userId).getFriends().add(friendId);
        userStorage.getById(friendId).getFriends().add(userId);
        return friendId;
    }
    public Long deleteFriend(Long userId, Long friendId) {
        validate(userId,friendId);
        userStorage.getById(userId).getFriends().remove(friendId);
        userStorage.getById(friendId).getFriends().remove(userId);
        return friendId;
    }
    public Set<Long> getFriends(Long userId) {
       return userStorage.getById(userId).getFriends();
    }
    public Set<Long> commonFriends(Long userId, Long friendId){
        var set1 = userStorage.getById(userId).getFriends();
        var set2 = userStorage.getById(friendId).getFriends();
        return set1.stream().filter(set2::contains).collect(Collectors.toSet());
    }

    private boolean validate(Long userId, Long friendId){
        User u = userStorage.getById(userId);
        User friend = userStorage.getById(friendId);
        if (Objects.equals(userId, friendId) || u == null || friend == null) {
            throw new UserNotFoundException("Unsupported operation");
        }
        return true;
    }
}
