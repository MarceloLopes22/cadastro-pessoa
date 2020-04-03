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

@RestController
@RequestMapping("/api")
@CrossOrigin(value = "*")
public class PessoaRestController {
	
	@Autowired
	private PessoaService service;
	
	@RequestMapping(value = "/pessoa/salvar", method = RequestMethod.POST , produces = "application/json")
	public ResponseEntity<Response<Pessoa>> salvar(@RequestBody Pessoa pessoa) {
		return this.service.create(pessoa);
	}
	
	@RequestMapping(value = "/pessoa/atualizar", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<Response<Pessoa>> atualizar(@RequestBody Pessoa pessoa) {
		return this.service.update(pessoa);
	}
	
	@RequestMapping(value = "/pessoa/deletar/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Response<Pessoa>> deletar(@PathVariable Long id) {
		return this.service.delete(id);
	}
	
	@GetMapping(value = "/pessoa/pesquisar/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<Pessoa>> pesquisar(@PathVariable Long id) {
		return this.service.findById(id);
	}
	
	@GetMapping(value = "/pessoa/listar/{page}/{count}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<Page<List<Pessoa>>>> listar(@PathVariable int page,
			@PathVariable int count) {
		return this.service.findAll(page, count);
	}

}
