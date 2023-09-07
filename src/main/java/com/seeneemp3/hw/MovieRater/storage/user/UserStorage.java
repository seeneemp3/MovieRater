package com.seeneemp3.hw.MovieRater.storage.user;

import com.seeneemp3.hw.MovieRater.model.User;

import java.util.List;

public interface UserStorage {
    List<User> getAll();

    User create(User user);

    User update(User user);

    User getById(Long userId);

    User delete(Long userId);

    void addFriend(Long userId, Long friendId);

    List<User> getCommon(Long userId, Long friendId);

}
