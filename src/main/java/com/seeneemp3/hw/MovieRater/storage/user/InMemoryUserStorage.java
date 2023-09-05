package com.seeneemp3.hw.MovieRater.storage.user;

import com.seeneemp3.hw.MovieRater.exception.UserNotFoundException;
import com.seeneemp3.hw.MovieRater.exception.UserValidationException;
import com.seeneemp3.hw.MovieRater.exception.ValidationException;
import com.seeneemp3.hw.MovieRater.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
public class InMemoryUserStorage implements UserStorage {
    private Map<Long, User> users;
    private Long currentId;


    public InMemoryUserStorage() {
        currentId = 0L;
        users = new HashMap<>();
    }
    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User create(User user) {
        user.setId(++currentId);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User update(User user) {
        if (user.getId() == null) {
            throw new UserValidationException("Wrong argument: id");
        }
        if (!users.containsKey(user.getId())) {
            throw new UserNotFoundException("No user with id " + user.getId() + " was founded");
        }
        users.put(user.getId(), user);

        return user;
    }

    @Override
    public User getById(Long userId) {
        if (!users.containsKey(userId)) {
            throw new UserNotFoundException("No user with id " + userId + " was founded");
        }
        return users.get(userId);
    }

    @Override
    public User delete(Long userId) {
        if (userId == null) {
            throw new ValidationException("Wrong argument: id");
        }
        if (!users.containsKey(userId)) {
            throw new UserNotFoundException("No user with id " + userId + " was founded");
        }
        return users.remove(userId);

    }

    @Override
    public void addFriend(Long userId, Long friendId) {

    }

    @Override
    public List<User> getCommon(Long userId, Long friendId) {
        return null;
    }
}
