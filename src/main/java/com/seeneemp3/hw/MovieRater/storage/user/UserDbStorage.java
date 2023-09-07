package com.seeneemp3.hw.MovieRater.storage.user;

import com.seeneemp3.hw.MovieRater.exception.UserNotFoundException;
import com.seeneemp3.hw.MovieRater.exception.UserValidationException;
import com.seeneemp3.hw.MovieRater.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Slf4j
@Primary
@Component("userDbStorage")
public class UserDbStorage implements UserStorage {
    //TODO: пересмотреть метод валидейт чтобы возвращал юзера или пару юзер-юзер
    private final JdbcTemplate jdbcTemplate;
    private final UserMapper userMapper;

    @Autowired
    public UserDbStorage(JdbcTemplate jdbcTemplate, UserMapper userMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.userMapper = userMapper;
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM users", userMapper);
    }

    @Override
    public User create(User user) {
        if ((user.getName() == null) || (user.getName().isEmpty()) || (user.getName().isBlank())) {
            user.setName(user.getLogin());
        }
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        Long userId = simpleJdbcInsert.executeAndReturnKey(user.toMap()).longValue();
        user.setId(userId);
        log.info("Added a new user with ID={}", user.getId());
        return user;
    }

    @Override
    public User update(User user) {
        validate(user.getId());
        if (getById(user.getId()) != null) {
            String sqlQuery = "UPDATE users SET " +
                    "email = ?, login = ?, name = ?, birthday = ? " +
                    "WHERE id = ?";
            jdbcTemplate.update(sqlQuery, user.getEmail(), user.getLogin(), user.getName(), user.getBirthday(), user.getId());
            log.info("User with ID={} has been successfully updated", user.getId());
            return user;
        } else {
            throw new UserNotFoundException("User with ID=" + user.getId() + " not found!");
        }
    }

    @Override
    public User getById(Long userId) {
        validate(userId);
        return jdbcTemplate.query("SELECT * FROM users WHERE id = ?", userMapper, userId)
                .stream()
                .findAny()
                .orElseThrow(() -> new UserNotFoundException("User with ID=" + userId + " not found!"));
    }

    @Override
    public User delete(Long userId) {
        validate(userId);
        User user = getById(userId);
        int res = jdbcTemplate.update("DELETE FROM users WHERE id = ?", userId);
        if (res == 0) {
            throw new UserNotFoundException("User with ID=" + userId + " not found!");
        }
        log.info("User with ID={} has been successfully deleted", user.getId());
        return user;
    }

    public void addFriend(Long userId, Long friendId) {
        validate(userId, friendId);
        User friend = getById(friendId);
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
        log.info("Adding friend with ID {} to user with ID {}", friendId, userId);
    }

    public List<User> getCommon(Long userId, Long friendId) {
        validate(userId, friendId);
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

    private void validate(Long userId) {
        if (userId == null) {
            throw new UserValidationException("Empty argument passed!");
        }
    }

    private void validate(Long userId, Long friendId) {
        User u = getById(userId);
        User friend = getById(friendId);
        if (Objects.equals(userId, friendId) || u == null || friend == null) {
            throw new UserNotFoundException("Unsupported operation");
        }
    }
}
