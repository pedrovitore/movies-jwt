package br.com.pedrodeveloper.moviesjwt.config;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import br.com.pedrodeveloper.moviesjwt.model.entities.Movie;
import br.com.pedrodeveloper.moviesjwt.repository.MovieRepository;

@Component
public class InitializeData implements ApplicationRunner {

	@Autowired
	private MovieRepository movieRepository;
	
	public void run(ApplicationArguments args) throws ParseException {
		//Qty of records to add
		Long max = 10l;
		
		Logger logger = LoggerFactory.getLogger(this.getClass());
		
		logger.info("Loading fake data into database...");
		
		Faker faker = new Faker(new Locale("pt-BR"));
		long count = movieRepository.count();
		
		for (long i = count; i<max; i++) {
			logger.info("Inserting line number " + i + "...");
			
			Movie movie = new Movie();
			movie.setTitle(faker.funnyName().name());
			movie.setDirector(faker.artist().name());
			movie.setOverview(faker.lorem().characters(1400));
			movie.setRating(new BigDecimal(faker.number().randomDouble(2, 1, 5)));
			movie.setReleaseDate(LocalDateTime.ofInstant(faker.date().past(3650, TimeUnit.DAYS).toInstant(), ZoneId.systemDefault()).toLocalDate());
			movie.setRuntime(LocalTime.of(faker.number().numberBetween(0, 2), faker.number().numberBetween(0, 59), faker.number().numberBetween(0, 59)));
			
			int numberOfGenres = faker.number().numberBetween(1, 4);
			for (int j = 1; j <= numberOfGenres; j++) {
				movie.addGenre(faker.music().genre());
			}
			
			movieRepository.save(movie);
		}
		

		logger.info("Fake data loaded.");
    }
}
