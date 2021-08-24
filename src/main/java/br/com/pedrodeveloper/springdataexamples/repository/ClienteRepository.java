package br.com.pedrodeveloper.springdataexamples.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import br.com.pedrodeveloper.springdataexamples.model.entities.Cliente;

public interface ClienteRepository extends PagingAndSortingRepository<Cliente, Integer>{


	@Query(value = "SELECT cliente "
			+ "FROM Cliente cliente "
			+ "WHERE (:name is null or cliente.nome like %:name%) "
			+ " AND (:initialDate is null or :initialDate <= cliente.cadastro) "
			+ " AND (:finalDate is null or :finalDate > cliente.cadastro) "
			+ "ORDER BY cliente.nome asc ",
			countQuery = "SELECT count(cliente) "
					+ "FROM Cliente cliente "
					+ "WHERE (:name is null or cliente.nome like %:name%) "
					+ " AND (:initialDate is null or :initialDate <= cliente.cadastro) "
					+ " AND (:finalDate is null or :finalDate > cliente.cadastro) ")
	public Page<Cliente> find(@Param("name") String name, 
							@Param("initialDate") LocalDateTime initialDate, 
							@Param("finalDate") LocalDateTime finalDate, 
							Pageable pageable);
}
