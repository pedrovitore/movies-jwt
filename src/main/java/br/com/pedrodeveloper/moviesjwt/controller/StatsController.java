package br.com.pedrodeveloper.moviesjwt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pedrodeveloper.moviesjwt.model.dto.GenreCount;
import br.com.pedrodeveloper.moviesjwt.model.dto.StatsDto;
import br.com.pedrodeveloper.moviesjwt.repository.MovieRepository;

@RestController
public class StatsController {

	@Autowired
	private MovieRepository movieRepository;
	
	@GetMapping("/stats")
	public StatsDto getStats() {
		long count = movieRepository.count();
		List<GenreCount> stats = movieRepository.getStats();
		
		StatsDto dto = new StatsDto((int) count, stats);
		
		return dto;
	}
}
