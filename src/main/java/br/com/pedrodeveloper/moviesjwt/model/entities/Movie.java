package br.com.pedrodeveloper.moviesjwt.model.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
	
	private String title;
	
	private LocalDate releaseDate;
	
	private String director;
	
	private String overview;
	
	private BigDecimal rating;
	
	private LocalTime runtime;
	
//	@ManyToMany
//	@JoinTable(name = "movie_genres", 
//			  joinColumns = @JoinColumn(name = "id_movie"), 
//			  inverseJoinColumns = @JoinColumn(name = "id_genre"))
//	private List<Genre> genres;
	@ElementCollection
	private List<String> genres;

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
	
	public void addGenre(String genre) {
		if (this.genres == null)
			this.genres = new ArrayList<>();
		
		this.genres.add(genre);
	}
	
	public void removeGenre(String genre) {
		if (this.genres == null)
			return;
		
		this.genres.remove(genre);
	}

}
