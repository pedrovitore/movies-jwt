package br.com.pedrodeveloper.moviesjwt.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.pedrodeveloper.moviesjwt.model.entities.User;

public interface UserRepository extends PagingAndSortingRepository<User, Integer>{
	
}
