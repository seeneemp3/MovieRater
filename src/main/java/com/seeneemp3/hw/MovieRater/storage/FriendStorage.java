package com.seeneemp3.hw.MovieRater.storage;

import com.seeneemp3.hw.MovieRater.exception.UserNotFoundException;
import com.seeneemp3.hw.MovieRater.model.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
@Primary
@Component
public class FriendStorage {
    private JdbcTemplate jdbcTemplate;
    private UserStorage userStorage;
    BeanPropertyRowMapper<User> userMapper = new BeanPropertyRowMapper<>(User.class);

    @Autowired
    public FriendStorage(JdbcTemplate jdbcTemplate, UserStorage userStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.userStorage = userStorage;
    }
    public void addFriend(Long userId, Long friendId){
        validate(userId,friendId);
        User friend = userStorage.getById(friendId);
        boolean status = false;
        if (friend.getFriends().contains(userId)) {
            status = true;
            String sql = """
                UPDATE friends
                SET user_id = ?,
                    friend_id = ?,
                    status = ?
                WHERE user_id = ?
                AND friend_id = ?""";
            jdbcTemplate.update(sql, userMapper, status);
        }
        String sql = "INSERT INTO friends (user_id, friend_id, status) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, userMapper, status);
    }

    public List<User> getCommon(Long userId, Long friendId){
        validate(userId,friendId);
        User friend = userStorage.getById(friendId);
       return jdbcTemplate.query(
                """
                SELECT *
                FROM users
                WHERE id IN (SELECT f1.friendId AS common
                FROM friends f1
                JOIN friends f2 ON f1.friendId = f2.friendId
                WHERE f1.userId = ?
                AND f2.userId = ?);"""
                , userMapper, userId, friendId);
    }




    private void validate(Long userId, Long friendId){
        User u = userStorage.getById(userId);
        User friend = userStorage.getById(friendId);
        if (Objects.equals(userId, friendId) || u == null || friend == null) {
            throw new UserNotFoundException("Unsupported operation");
        }
    }
}
