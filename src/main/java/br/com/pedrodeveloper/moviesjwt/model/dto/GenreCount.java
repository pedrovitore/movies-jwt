package br.com.pedrodeveloper.moviesjwt.model.dto;

public class GenreCount {

	private String genre;
	private Long count;
	
	public GenreCount(String genre, Long count) {
		super();
		this.genre = genre;
		this.count = count;
	}
	
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
}
