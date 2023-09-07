package com.seeneemp3.hw.MovieRater;

import com.seeneemp3.hw.MovieRater.model.Genre;
import com.seeneemp3.hw.MovieRater.model.Movie;
import com.seeneemp3.hw.MovieRater.model.Mpa;
import com.seeneemp3.hw.MovieRater.model.User;
import com.seeneemp3.hw.MovieRater.service.MovieService;
import com.seeneemp3.hw.MovieRater.service.UserService;
import com.seeneemp3.hw.MovieRater.storage.movie.MovieDbStorage;
import com.seeneemp3.hw.MovieRater.storage.user.UserDbStorage;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.AssertionsForClassTypes;
import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureCache
@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class MovieRaterApplicationTests {
	private final UserDbStorage userStorage;
	private final MovieDbStorage movieStorage;
	private final MovieService movieService;
	private final UserService userService;
	private User firstUser;
	private User secondUser;
	private User thirdUser;
	private Movie firstMovie;
	private Movie secondMovie;
	private Movie thirdMovie;

	@BeforeEach
	public void beforeEach() {
		firstUser = User.builder()
				.name("GeorgTheFirst")
				.login("First")
				.email("1@google.com")
				.birthday(LocalDate.of(1980, 12, 23))
				.build();

		secondUser = User.builder()
				.name("GeorgTheSecond")
				.login("Second")
				.email("2@google.com")
				.birthday(LocalDate.of(1980, 12, 24))
				.build();

		thirdUser = User.builder()
				.name("GeorgTheThird")
				.login("Third")
				.email("3@google.com")
				.birthday(LocalDate.of(1980, 12, 25))
				.build();

		firstMovie = Movie.builder()
				.name("Breakfast at Tiffany's")
				.description("American romantic comedy film directed by Blake Edwards, written by George Axelrod," +
						" adapted from Truman Capote's 1958 novella of the same name.")
				.releaseDate(LocalDate.of(1961, 10, 5))
				.duration(114)
				.build();
		firstMovie.setMpa(new Mpa(1, "G"));
		firstMovie.setLikes(new HashSet<>());
		firstMovie.setGenres(new HashSet<>(Arrays.asList(new Genre(2, "Drama"),
				new Genre(1, "Комедия"))));

		secondMovie = Movie.builder()
				.name("Avatar")
				.description("American epic science fiction film directed, written, produced, and co-edited" +
						" by James Cameron. It is set in the mid-22nd century when humans are colonizing Pandora...")
				.releaseDate(LocalDate.of(2009, 12, 10))
				.duration(162)
				.build();
		secondMovie.setMpa(new Mpa(3, "PG-13"));
		secondMovie.setLikes(new HashSet<>());
		secondMovie.setGenres(new HashSet<>(List.of(new Genre(6, "Thriller"))));

		thirdMovie = Movie.builder()
				.name("One Flew Over the Cuckoo's Nest")
				.description("American psychological comedy drama film directed by Milos Forman, based on" +
						" the 1962 novel of the same name by Ken Kesey. The film stars Jack Nicholson...")
				.releaseDate(LocalDate.of(1975, 11, 19))
				.duration(133)
				.build();
		thirdMovie.setMpa(new Mpa(4, "R"));
		thirdMovie.setLikes(new HashSet<>());
		thirdMovie.setGenres(new HashSet<>(List.of(new Genre(2, "Drama"))));
	}

	@Test
	public void testCreateUserAndGetUserById() {
		firstUser = userStorage.create(firstUser);
		Optional<User> userOptional = Optional.ofNullable(userStorage.getById(firstUser.getId()));
		assertThat(userOptional)
				.hasValueSatisfying(user ->
						assertThat(user)
								.hasFieldOrPropertyWithValue("id", firstUser.getId())
								.hasFieldOrPropertyWithValue("name", "GeorgTheFirst"));
	}

	@Test
	public void testGetUsers() {
		firstUser = userStorage.create(firstUser);
		secondUser = userStorage.create(secondUser);
		List<User> listUsers = userStorage.getAll();
		AssertionsForInterfaceTypes.assertThat(listUsers).contains(firstUser);
		AssertionsForInterfaceTypes.assertThat(listUsers).contains(secondUser);
	}

	@Test
	public void testUpdateUser() {
		firstUser = userStorage.create(firstUser);
		User updateUser = User.builder()
				.id(firstUser.getId())
				.name("UpdateGeorgTheFirst")
				.login("First")
				.email("1@google.com")
				.birthday(LocalDate.of(1980, 12, 23))
				.build();
		Optional<User> testUpdateUser = Optional.ofNullable(userStorage.update(updateUser));
		assertThat(testUpdateUser)
				.hasValueSatisfying(user -> assertThat(user)
						.hasFieldOrPropertyWithValue("name", "UpdateGeorgTheFirst")
				);
	}

	@Test
	public void deleteUser() {
		firstUser = userStorage.create(firstUser);
		userStorage.delete(firstUser.getId());
		List<User> listUsers = userStorage.getAll();
		AssertionsForInterfaceTypes.assertThat(listUsers).hasSize(0);
	}

	@Test
	public void testCreateMovieAndGetMovieById() {
		firstMovie = movieStorage.create(firstMovie);
		Optional<Movie> movieOptional = Optional.ofNullable(movieStorage.getById(firstMovie.getId()));
		assertThat(movieOptional)
				.hasValueSatisfying(movie -> assertThat(movie)
						.hasFieldOrPropertyWithValue("id", firstMovie.getId())
						.hasFieldOrPropertyWithValue("name", "Breakfast at Tiffany's")
				);
	}

	@Test
	public void testGetMovies() {
		firstMovie = movieStorage.create(firstMovie);
		secondMovie = movieStorage.create(secondMovie);
		thirdMovie = movieStorage.create(thirdMovie);
		List<Movie> listMovies = movieStorage.getAll();
		AssertionsForInterfaceTypes.assertThat(listMovies).contains(firstMovie);
		AssertionsForInterfaceTypes.assertThat(listMovies).contains(secondMovie);
		AssertionsForInterfaceTypes.assertThat(listMovies).contains(thirdMovie);
	}

	@Test
	public void testUpdateMovie() {
		firstMovie = movieStorage.create(firstMovie);
		Movie updateMovie = Movie.builder()
				.id(firstMovie.getId())
				.name("UpdateName")
				.description("UpdateDescription")
				.releaseDate(LocalDate.of(1975, 11, 19))
				.duration(133)
				.build();
		updateMovie.setMpa(new Mpa(1, "G"));
		Optional<Movie> testUpdateMovie = Optional.ofNullable(movieStorage.update(updateMovie));
		assertThat(testUpdateMovie)
				.hasValueSatisfying(movie ->
						assertThat(movie)
								.hasFieldOrPropertyWithValue("name", "UpdateName")
								.hasFieldOrPropertyWithValue("description", "UpdateDescription")
				);
	}

	@Test
	public void deleteMovie() {
		firstMovie = movieStorage.create(firstMovie);
		secondMovie = movieStorage.create(secondMovie);
		movieStorage.delete(firstMovie.getId());
		List<Movie> listMovies = movieStorage.getAll();
		AssertionsForInterfaceTypes.assertThat(listMovies).hasSize(1);
		assertThat(Optional.of(listMovies.get(0)))
				.hasValueSatisfying(movie ->
						AssertionsForClassTypes.assertThat(movie)
								.hasFieldOrPropertyWithValue("name", "Avatar"));
	}

	@Test
	public void testAddLike() {
		firstUser = userStorage.create(firstUser);
		firstMovie = movieStorage.create(firstMovie);
		movieService.addLike(firstMovie.getId(), firstUser.getId());
		firstMovie = movieStorage.getById(firstMovie.getId());
		AssertionsForInterfaceTypes.assertThat(firstMovie.getLikes()).hasSize(1);
		AssertionsForInterfaceTypes.assertThat(firstMovie.getLikes()).contains(firstUser.getId());
	}

	@Test
	public void testDeleteLike() {
		firstUser = userStorage.create(firstUser);
		secondUser = userStorage.create(secondUser);
		firstMovie = movieStorage.create(firstMovie);
		movieService.addLike(firstMovie.getId(), firstUser.getId());
		movieService.addLike(firstMovie.getId(), secondUser.getId());
		movieService.removeLike(firstMovie.getId(), firstUser.getId());
		firstMovie = movieStorage.getById(firstMovie.getId());
		AssertionsForInterfaceTypes.assertThat(firstMovie.getLikes()).hasSize(1);
		AssertionsForInterfaceTypes.assertThat(firstMovie.getLikes()).contains(secondUser.getId());
	}

	@Test
	public void testGetPopularMovies() {

		firstUser = userStorage.create(firstUser);
		secondUser = userStorage.create(secondUser);
		thirdUser = userStorage.create(thirdUser);

		firstMovie = movieStorage.create(firstMovie);
		movieService.addLike(firstMovie.getId(), firstUser.getId());

		secondMovie = movieStorage.create(secondMovie);
		movieService.addLike(secondMovie.getId(), firstUser.getId());
		movieService.addLike(secondMovie.getId(), secondUser.getId());
		movieService.addLike(secondMovie.getId(), thirdUser.getId());

		thirdMovie = movieStorage.create(thirdMovie);
		movieService.addLike(thirdMovie.getId(), firstUser.getId());
		movieService.addLike(thirdMovie.getId(), secondUser.getId());

		List<Long> listMovies = movieService.mostLiked(5);

		AssertionsForInterfaceTypes.assertThat(listMovies).hasSize(3);

		assertThat(Optional.of(listMovies.get(0)))
				.isNotEmpty()
				.contains(1L);

		assertThat(Optional.of(listMovies.get(1)))
				.isNotEmpty()
				.contains(2L);

		assertThat(Optional.of(listMovies.get(2)))
				.isNotEmpty()
				.contains(3L);
	}

	@Test
	public void testAddFriend() {
		firstUser = userStorage.create(firstUser);
		secondUser = userStorage.create(secondUser);
		userService.addFriend(firstUser.getId(), secondUser.getId());
		AssertionsForInterfaceTypes.assertThat(userService.getFriends(firstUser.getId())).hasSize(1);
		AssertionsForInterfaceTypes.assertThat(userService.getFriends(firstUser.getId())).contains(secondUser.getId());
	}

	@Test
	public void testDeleteFriend() {
		firstUser = userStorage.create(firstUser);
		secondUser = userStorage.create(secondUser);
		thirdUser = userStorage.create(thirdUser);
		userService.addFriend(firstUser.getId(), secondUser.getId());
		userService.addFriend(firstUser.getId(), thirdUser.getId());
		userService.deleteFriend(firstUser.getId(), secondUser.getId());
		AssertionsForInterfaceTypes.assertThat(userService.getFriends(firstUser.getId())).hasSize(1);
		AssertionsForInterfaceTypes.assertThat(userService.getFriends(firstUser.getId())).contains(thirdUser.getId());
	}

	@Test
	public void testGetFriends() {
		firstUser = userStorage.create(firstUser);
		secondUser = userStorage.create(secondUser);
		thirdUser = userStorage.create(thirdUser);
		userService.addFriend(firstUser.getId(), secondUser.getId());
		userService.addFriend(firstUser.getId(), thirdUser.getId());
		AssertionsForInterfaceTypes.assertThat(userService.getFriends(firstUser.getId())).hasSize(2);
		AssertionsForInterfaceTypes.assertThat(userService.getFriends(firstUser.getId())).contains(secondUser.getId(), thirdUser.getId());
	}

	@Test
	public void testGetCommonFriends() {
		firstUser = userStorage.create(firstUser);
		secondUser = userStorage.create(secondUser);
		thirdUser = userStorage.create(thirdUser);
		userService.addFriend(firstUser.getId(), secondUser.getId());
		userService.addFriend(firstUser.getId(), thirdUser.getId());
		userService.addFriend(secondUser.getId(), firstUser.getId());
		userService.addFriend(secondUser.getId(), thirdUser.getId());
		AssertionsForInterfaceTypes.assertThat(userService.commonFriends(firstUser.getId(), secondUser.getId())).hasSize(1);
		AssertionsForInterfaceTypes.assertThat(userService.commonFriends(firstUser.getId(), secondUser.getId()))
				.contains(thirdUser.getId());
	}
}

