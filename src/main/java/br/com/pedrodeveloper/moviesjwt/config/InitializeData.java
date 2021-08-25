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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import br.com.pedrodeveloper.moviesjwt.model.entities.Movie;
import br.com.pedrodeveloper.moviesjwt.model.entities.User;
import br.com.pedrodeveloper.moviesjwt.repository.MovieRepository;
import br.com.pedrodeveloper.moviesjwt.repository.UserRepository;

@Component
public class InitializeData implements ApplicationRunner {

	@Value("${fakedata.limit}")
	private Long limit;
	
	@Autowired
	private MovieRepository movieRepository;
	@Autowired
	private UserRepository userRepository;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${spring.security.user.name}")
	private String defaultUser;
	@Value("${spring.security.user.password}")
	private String defaultPass;
	@Autowired
	private PasswordEncoder encoder;
	
	public void run(ApplicationArguments args) throws ParseException {
		insertDefaultUser();
		loadFakeData();
    }
	
	private void insertDefaultUser() {
		if (userRepository.count() == 0l) {
			logger.info("No users available on the database - Creating default user with user/pass = " + this.defaultUser + "/" + this.defaultPass);
			
			User user  = new User();
			user.setUser(this.defaultUser);
			user.setPass(encoder.encode(this.defaultPass));
			userRepository.save(user);

			logger.info("Default user created successfully :)");
		}
	}

	private void loadFakeData() {
		if (limit == 0l) {
			logger.info("fakedata.limit is set to zero. No initial data will be loaded.");
			return;
		}
		
		//Qty of records to add
		Long max = limit;
		
		
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
