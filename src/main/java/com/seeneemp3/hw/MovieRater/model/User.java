package com.seeneemp3.hw.MovieRater.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.*;

@Data
public class User {
    private Long id;
    @Email(message = "Invalid email format.")
    @NonNull
    @NotBlank
    private String email;
    @NotBlank
    @Pattern(regexp = "\\S*$")
    private String login;
    @PastOrPresent
    private LocalDate birthday;
    private String name;
    private Map<Long, Boolean> friendStatus = new HashMap<>();

    public User(Long id, @NonNull String email, String login, @JsonFormat(pattern = "yyyy-MM-dd") LocalDate birthday, String name) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.birthday = birthday;
        if((name == null) || (name.isEmpty()) || (name.isBlank())){this.name = login;
        }else this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email) && Objects.equals(login, user.login) && Objects.equals(birthday, user.birthday) && Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, login, birthday, name);
    }
    public Map<String, Object> toMap() {
        Map<String, Object> values = new HashMap<>();
        values.put("email", email);
        values.put("login", login);
        values.put("name", name);
        values.put("birthday", birthday);
        return values;
    }
}
