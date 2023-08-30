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
        userStorage.getById(userId).getFriendStatus().put(friendId, false);
        userStorage.getById(friendId).getFriendStatus().put(userId, false);
        return friendId;
    }
    public Long deleteFriend(Long userId, Long friendId) {
        validate(userId,friendId);
        userStorage.getById(userId).getFriendStatus().remove(friendId);
        userStorage.getById(friendId).getFriendStatus().remove(userId);
        return friendId;
    }
    public Set<Long> getFriends(Long userId) {
       return userStorage.getById(userId).getFriendStatus().entrySet().stream().filter(Map.Entry::getValue).map(Map.Entry::getKey).collect(Collectors.toSet());
    }
    public Set<Long> commonFriends(Long userId, Long friendId){
        var set1 = userStorage.getById(userId).getFriendStatus().entrySet().stream().filter(Map.Entry::getValue).map(Map.Entry::getKey).collect(Collectors.toCollection(HashSet::new));
        var set2 = userStorage.getById(friendId).getFriendStatus().entrySet().stream().filter(Map.Entry::getValue).map(Map.Entry::getKey).collect(Collectors.toCollection(HashSet::new));
        return set1.stream().filter(set2::contains).collect(Collectors.toSet());
    }
    public Long acceptFriend(Long userId, Long friendId){
        validate(userId,friendId);
        if ( userStorage.getById(userId).getFriendStatus().containsKey(friendId)){
            userStorage.getById(userId).getFriendStatus().put(friendId, true);
            userStorage.getById(friendId).getFriendStatus().put(userId, true);
        }
        return friendId;
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
