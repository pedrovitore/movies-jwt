package br.com.pedrodeveloper.springdataexamples.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.pedrodeveloper.springdataexamples.model.entities.Cliente;
import br.com.pedrodeveloper.springdataexamples.repository.ClienteRepository;
import br.com.pedrodeveloper.springdataexamples.util.StringValidationUtils;

@Controller
public class ClienteController {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@GetMapping("/clientes")
	public String getClientes(
			@RequestParam(required = false) String page, 
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String initialDate,
			@RequestParam(required = false) String finalDate, 
			Model model) {
		Integer pageNumber = StringValidationUtils.stringToPageNumber(page);
		if (name != null) name =  name.replace(" ", "%");
		LocalDateTime initialDateConverted = StringValidationUtils.stringToDate(initialDate);
		LocalDateTime finalDateConverted = StringValidationUtils.stringToDate(finalDate);
		
		//Add 1 dia na finalDate e na query compara usando "<" para pegar todos os horários do dia
		if (finalDateConverted != null)
			finalDateConverted = finalDateConverted.plusDays(1l);
		
		Page<Cliente> clientes = clienteRepository.find(name, initialDateConverted, finalDateConverted, PageRequest.of(pageNumber, 20));
		
		model.addAttribute("clientes", clientes);
		
		return "clientes";
	}
}
