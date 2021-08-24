package br.com.pedrodeveloper.springdataexamples.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.pedrodeveloper.springdataexamples.model.entities.Venda;

public interface VendaRepository extends PagingAndSortingRepository<Venda, Integer>{

}
