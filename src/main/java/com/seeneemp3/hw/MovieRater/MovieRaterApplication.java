package com.seeneemp3.hw.MovieRater;

import com.seeneemp3.hw.MovieRater.model.User;
import com.seeneemp3.hw.MovieRater.storage.InMemoryUserStorage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServer;

import java.time.LocalDate;

@SpringBootApplication
public class MovieRaterApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieRaterApplication.class, args);


	}

}
