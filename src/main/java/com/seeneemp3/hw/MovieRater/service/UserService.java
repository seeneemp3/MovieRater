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
    private final UserStorage userStorage;
    private final FriendStorage friendStorage;

    @Autowired
    public UserService(UserStorage userStorage, FriendStorage friendStorage) {
        this.userStorage = userStorage;
        this.friendStorage = friendStorage;
    }

    public Long addFriend(Long userId, Long friendId) {
        validate(userId, friendId);
        friendStorage.addFriend(userId, friendId);
        return friendId;
    }

    public Long deleteFriend(Long userId, Long friendId) {
        validate(userId, friendId);
        friendStorage.deleteFriend(userId, friendId);
        return friendId;
    }

    public Set<Long> getFriends(Long userId) {
        return new HashSet<>(friendStorage.getFriends(userId));
    }

    public Set<Long> commonFriends(Long userId, Long friendId) {
        return new HashSet<>(friendStorage.getCommon(userId, friendId));
    }

    private void validate(Long userId, Long friendId) {
        User u = userStorage.getById(userId);
        User friend = userStorage.getById(friendId);
        if (Objects.equals(userId, friendId) || u == null || friend == null) {
            throw new UserNotFoundException("Unsupported operation");
        }
    }
}
