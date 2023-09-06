package com.seeneemp3.hw.MovieRater.service;

import com.seeneemp3.hw.MovieRater.exception.UserNotFoundException;
import com.seeneemp3.hw.MovieRater.model.User;
import com.seeneemp3.hw.MovieRater.storage.friend.FriendStorage;
import com.seeneemp3.hw.MovieRater.storage.user.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
public class UserService {
    private UserStorage userStorage;
    private FriendStorage friendStorage;
    @Autowired
    public UserService(UserStorage userStorage, FriendStorage friendStorage) {
        this.userStorage = userStorage;
        this.friendStorage = friendStorage;
    }

    public Long addFriend(Long userId, Long friendId) {
        validate(userId,friendId);
        friendStorage.addFriend(userId, friendId);
        return friendId;
    }
    public Long deleteFriend(Long userId, Long friendId) {
        validate(userId,friendId);
        friendStorage.deleteFriend(userId, friendId);
        return friendId;
    }
    public Set<User> getFriends(Long userId) {
       return new HashSet<>(friendStorage.getFriends(userId));
    }
    public Set<User> commonFriends(Long userId, Long friendId){
//        var set1 = userStorage.getById(userId).getFriends();
//        var set2 = userStorage.getById(friendId).getFriends();
//        return set1.stream().filter(set2::contains).collect(Collectors.toSet());
        return new HashSet<>(friendStorage.getCommon(userId, friendId));
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
