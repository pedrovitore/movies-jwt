package br.com.pedrodeveloper.moviesjwt.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.pedrodeveloper.moviesjwt.model.entities.Movie;

public class MovieAddDto {

	@NotEmpty
	@Size(max = 250)
	private String title;
	
	@NotNull
	private LocalDate releaseDate;
	
	private String director;
	
	private String overview;
	
	@NotNull
	@DecimalMax(value = "100.00", message = "Rating must be lower than 100")
	@DecimalMin(value = "0.00", message = "Rating must be greater than 0")
	private BigDecimal rating;
	
	@NotNull
	private LocalTime runtime;
	
	@NotNull
	@Size(min = 1, message = "Please inform at least 1 genre")
	private List<String> genres;
	
	/**
	 * Gets a Movie entity based on this dto ready to persist.
	 */
	@JsonIgnore
	public Movie getEntity() {
		Movie entity = new Movie();

		entity.setTitle(getTitle());
		entity.setReleaseDate(getReleaseDate());
		entity.setDirector(getDirector());
		entity.setOverview(getOverview());
		entity.setRating(getRating());
		entity.setRuntime(getRuntime());
		entity.setGenres(getGenres());
		
		return entity;
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
