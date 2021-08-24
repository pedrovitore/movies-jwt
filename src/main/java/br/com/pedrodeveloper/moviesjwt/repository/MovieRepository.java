package br.com.pedrodeveloper.moviesjwt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.pedrodeveloper.moviesjwt.model.dto.GenreCount;
import br.com.pedrodeveloper.moviesjwt.model.entities.Movie;

public interface MovieRepository extends PagingAndSortingRepository<Movie, Integer>{
	
	@Query(value = "SELECT new br.com.pedrodeveloper.moviesjwt.model.dto.GenreCount("
						+ " genre, count(genre) "
						+ " ) "
			+ " FROM Movie movie LEFT JOIN movie.genres genre "
			+ " GROUP BY genre")
	public List<GenreCount> getStats();
}
