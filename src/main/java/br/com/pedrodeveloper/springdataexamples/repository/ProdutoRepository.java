package br.com.pedrodeveloper.springdataexamples.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.pedrodeveloper.springdataexamples.model.entities.Produto;

public interface ProdutoRepository extends PagingAndSortingRepository<Produto, Integer>{

}
