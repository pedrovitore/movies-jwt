package br.com.pedrodeveloper.moviesjwt.config;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import br.com.pedrodeveloper.moviesjwt.model.entities.User;

@Repository
@Transactional
public class UserDetailsImpl implements UserDetailsService {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			List<User> usersFound = manager.createQuery(
					"select user "
					+ "from User user "
					+ "where user.user like :user", User.class)
					.setParameter("user", username).getResultList();
			
			if (usersFound.size() == 0) throw new RuntimeException();
			
			return usersFound.get(0); 
		} catch (Exception e) {
			throw new UsernameNotFoundException("userName '" + username + "' not found.");
		}
	}

}
