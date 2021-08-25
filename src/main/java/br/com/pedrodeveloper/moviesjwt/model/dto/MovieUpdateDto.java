package br.com.pedrodeveloper.moviesjwt.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.pedrodeveloper.moviesjwt.model.entities.Movie;

public class MovieUpdateDto {

	@NotNull
    private Integer id;

	@Size(min = 1, max = 250)
	private String title;
	
	private LocalDate releaseDate;
	
	private String director;
	
	private String overview;
	
	@DecimalMax(value = "100.00", message = "Rating must be lower than 100")
	@DecimalMin(value = "0.00", message = "Rating must be greater than 0")
	private BigDecimal rating;
	
	private LocalTime runtime;
	
	@Size(min = 1, message = "Please inform at least 1 genre")
	private List<String> genres;
	
	/**
	 * Gets a Movie entity based on this dto ready to persist.
	 */
	@JsonIgnore
	public Movie updateEntity(Movie movie) {
		
		if (getTitle() != null)
			movie.setTitle(getTitle());
		
		if (getReleaseDate() != null)
			movie.setReleaseDate(getReleaseDate());
		
		if (getDirector() != null)
			movie.setDirector(getDirector());
		
		if (getOverview() != null)
			movie.setOverview(getOverview());
		
		if (getRating() != null)
			movie.setRating(getRating());
		
		if (getRuntime() != null)
			movie.setRuntime(getRuntime());
		
		if (getGenres() != null) {
			movie.setGenres(getGenres());
		}
		
		return movie;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public BigDecimal getRating() {
		return rating;
	}

	public void setRating(BigDecimal rating) {
		this.rating = rating;
	}

	public LocalTime getRuntime() {
		return runtime;
	}

	public void setRuntime(LocalTime runtime) {
		this.runtime = runtime;
	}

	public List<String> getGenres() {
		return genres;
	}

	public void setGenres(List<String> genres) {
		this.genres = genres;
	}
}
