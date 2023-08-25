package com.seeneemp3.hw.MovieRater.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Data
@Builder
public class User {
    private Long id;
    @Email(message = "Invalid email format.")
    @NonNull
    @NotBlank
    private String email;
    @NotBlank
    @Pattern(regexp = "\\S*$")
    private String login;
    @JsonFormat(pattern = "dd.MM.yyyy")
    @PastOrPresent
    private LocalDate birthday;
    private String name;
    private Set<Long> friends;

    public User(Long id, @NonNull String email, String login, LocalDate birthday, String name) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.birthday = birthday;
        this.name = name;
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
}
