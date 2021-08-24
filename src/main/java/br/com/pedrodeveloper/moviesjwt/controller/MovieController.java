package br.com.pedrodeveloper.moviesjwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pedrodeveloper.moviesjwt.model.entities.Movie;
import br.com.pedrodeveloper.moviesjwt.repository.MovieRepository;

@RestController
public class MovieController {

	@Autowired
	private MovieRepository movieRepository;
	
	@GetMapping("/movies")
	public Iterable<Movie> getMovies() {
		return movieRepository.findAll();
	}
}
