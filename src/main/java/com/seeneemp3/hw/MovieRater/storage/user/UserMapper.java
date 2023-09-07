package com.seeneemp3.hw.MovieRater.storage.user;

import com.seeneemp3.hw.MovieRater.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

@Component
public class UserMapper implements RowMapper<User> {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public UserMapper(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Long> getFriends(Long userId) {
        String sql = """
                SELECT friend_id
                FROM friends
                INNER JOIN users ON friends.friend_id = users.id
                WHERE friends.user_id = ?
                """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("friend_id"), userId);
    }

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User u = User.builder().build();
        u.setId(rs.getLong("id"));
        u.setName(rs.getString("name"));
        u.setLogin(rs.getString("login"));
        u.setEmail(rs.getString("email"));
        u.setBirthday(rs.getDate("birthday").toLocalDate());
        u.setFriends(new HashSet<>(getFriends(u.getId())));
        return u;
    }
}
