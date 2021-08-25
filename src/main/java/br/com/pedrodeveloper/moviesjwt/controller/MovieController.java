package br.com.pedrodeveloper.moviesjwt.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.pedrodeveloper.moviesjwt.model.dto.MovieAddDto;
import br.com.pedrodeveloper.moviesjwt.model.dto.MovieUpdateDto;
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
	
	@GetMapping("/movies/{id}")
	public Movie getMovieById(@PathVariable(required = true) Integer id) {
		Movie movie = movieRepository.findById(id).orElse(null);
		
		if (movie == null)
			throw new IllegalArgumentException("Movie Id not found.");
		
		return movie;
	}
	
	@PostMapping("/movies")
	public Movie addMovie(@RequestBody @Valid MovieAddDto dto) {
		return movieRepository.save(dto.getEntity());
	}
	
	@PutMapping("/movies")
	public Movie updateMovie(@RequestBody @Valid MovieUpdateDto dto) {
		Movie movie = movieRepository.findById(dto.getId()).orElse(null);
		
		if (movie == null)
			throw new IllegalArgumentException("Movie Id not found.");
		
		dto.updateEntity(movie);
		movieRepository.save(movie);
		
		return movie;
	}
	
	@DeleteMapping("/movies/{id}")
	public ResponseEntity<?> deleteMovie(@PathVariable(required = true) Integer id) {
		Movie movie = movieRepository.findById(id).orElse(null);
		
		if (movie == null)
			throw new IllegalArgumentException("Movie Id not found.");
		
		movieRepository.delete(movie);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	

	/**
	 * Tratamento de erro para algum erro de validacao das constraints.
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Object retornaErroValidacaoConstraint(MethodArgumentNotValidException ex, HttpServletRequest request) {
		Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
			.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
		return errors;
	}

	/**
	 * Tratamento de erro para algum erro de validacao das constraints.
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IllegalArgumentException.class)
	public Object retornaErroIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
		Map<String, String> errors = new HashMap<String, String>();
		errors.put("error", ex.getMessage());
		return errors;
	}
}
