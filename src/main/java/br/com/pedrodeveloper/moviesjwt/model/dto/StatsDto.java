package br.com.pedrodeveloper.moviesjwt.model.dto;

import java.util.List;

public class StatsDto {

	private Integer moviesAvailable;
	private List<GenreCount> moviesAvailableByGenre;
	
	public StatsDto(Integer moviesAvailable, List<GenreCount> moviesAvailableByGenre) {
		super();
		this.moviesAvailable = moviesAvailable;
		this.moviesAvailableByGenre = moviesAvailableByGenre;
	}
	
	public Integer getMoviesAvailable() {
		return moviesAvailable;
	}
	public void setMoviesAvailable(Integer moviesAvailable) {
		this.moviesAvailable = moviesAvailable;
	}
	public List<GenreCount> getMoviesAvailableByGenre() {
		return moviesAvailableByGenre;
	}
	public void setMoviesAvailableByGenre(List<GenreCount> moviesAvailableByGenre) {
		this.moviesAvailableByGenre = moviesAvailableByGenre;
	}
	
}
