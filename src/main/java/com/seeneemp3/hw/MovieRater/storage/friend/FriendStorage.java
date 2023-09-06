package com.seeneemp3.hw.MovieRater.storage.friend;

import com.seeneemp3.hw.MovieRater.exception.UserNotFoundException;
import com.seeneemp3.hw.MovieRater.model.User;
import com.seeneemp3.hw.MovieRater.storage.user.UserMapper;
import com.seeneemp3.hw.MovieRater.storage.user.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
@Primary
@Component
public class FriendStorage {
    private JdbcTemplate jdbcTemplate;
    private UserStorage userStorage;

    @Autowired
    public FriendStorage(JdbcTemplate jdbcTemplate, UserStorage userStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.userStorage = userStorage;
    }

    public List<Long> getFriends(Long userId) {
        User user = userStorage.getById(userId);
        if (user != null) {
            String sql = """
                    SELECT friend_id
                    FROM friends
                    INNER JOIN users ON friends.friend_id = users.id
                    WHERE friends.user_id = ?
                    """;
            return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("friend_id"), userId);
        } else {
            return null;
        }
    }


    public void addFriend(Long userId, Long friendId){
        validate(userId,friendId);
        User friend = userStorage.getById(friendId);
        boolean status = false;
        if(friend.getFriends() == null) { friend.setFriends(new HashSet<>());};
        if (friend.getFriends().contains(userId)) {
            status = true;
            String sql = """
                UPDATE friends
                SET user_id = ?,
                    friend_id = ?,
                    status = ?
                WHERE user_id = ?
                AND friend_id = ?""";
            jdbcTemplate.update(sql, friendId, userId, true, friendId, userId);
        }
        String sql = "INSERT INTO friends (user_id, friend_id, status) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, userId, friendId, status);
    }
    public void deleteFriend(Long userId, Long friendId) {
        User user = userStorage.getById(userId);
        User friend = userStorage.getById(friendId);
        if ((user != null) && (friend != null)) {
            String sql = "DELETE FROM friends WHERE user_id = ? AND friend_id = ?";
            jdbcTemplate.update(sql, userId, friendId);

            if (friend.getFriends().contains(userId)) {
                sql = "UPDATE friends SET user_id = ? AND friend_id = ? AND status = ? " +
                        "WHERE user_id = ? AND friend_id = ?";
                jdbcTemplate.update(sql, friendId, userId, false, friendId, userId);
            }
        }
    }

    public List<Long> getCommon(Long userId, Long friendId){
        validate(userId,friendId);
       return jdbcTemplate.query(
                """
                SELECT id
                FROM users
                WHERE id IN (SELECT f1.friend_id AS common
                FROM friends f1
                JOIN friends f2 ON f1.friend_id = f2.friend_id
                WHERE f1.user_id = ?
                AND f2.user_id = ?);"""
                , (rs, rowNum) -> rs.getLong("id"), userId, friendId);
    }




    private void validate(Long userId, Long friendId){
        User u = userStorage.getById(userId);
        User friend = userStorage.getById(friendId);
        if (Objects.equals(userId, friendId) || u == null || friend == null) {
            throw new UserNotFoundException("Unsupported operation");
        }
    }
}
