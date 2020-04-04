package com.cadastro.pessoa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cadastro.pessoa.basica.Pessoa;
import com.cadastro.pessoa.controller.response.Response;
import com.cadastro.pessoa.service.PessoaService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/pessoa/")
public class PessoaRestController {
	
	@Autowired
	private PessoaService service;
	
	@RequestMapping(value = "salvar", method = RequestMethod.POST , produces = "application/json")
	public ResponseEntity<Response<Pessoa>> salvar(@RequestBody Pessoa pessoa) {
		return this.service.create(pessoa);
	}
	
	@RequestMapping(value = "atualizar", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<Response<Pessoa>> atualizar(@RequestBody Pessoa pessoa) {
		return this.service.update(pessoa);
	}
	
	@RequestMapping(value = "deletar/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Response<Pessoa>> deletar(@PathVariable Long id) {
		return this.service.delete(id);
	}
	
	//@GetMapping(value = "pesquisar/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "pesquisar/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<Pessoa>> pesquisar(@PathVariable(name = "id") Long id) {
		return this.service.findById(id);
	}
	
	@GetMapping(value = "listar/{page}/{count}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<Page<List<Pessoa>>>> listar(@PathVariable(name = "page") int page,
			@PathVariable(name = "count") int count) {
		return this.service.findAll(page, count);
	}

}
