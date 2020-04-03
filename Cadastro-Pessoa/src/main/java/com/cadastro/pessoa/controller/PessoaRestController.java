package com.cadastro.pessoa.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cadastro.pessoa.basica.Pessoa;
import com.cadastro.pessoa.controller.response.Response;
import com.cadastro.pessoa.service.PessoaService;

@RestController
@RequestMapping("/api")
public class PessoaRestController {
	
	@Autowired
	private PessoaService service;
	
	@RequestMapping(value = "/pessoa/salvar", method = RequestMethod.POST , produces = "application/json")
	public ResponseEntity<Response<Pessoa>> salvar(@RequestBody @Valid Pessoa pessoa) {
		return this.service.create(pessoa);
	}
	
	@RequestMapping(value = "/pessoa/atualizar", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<Response<Pessoa>> atualizar(@RequestBody @Valid Pessoa pessoa) {
		return this.service.update(pessoa);
	}
	
	@RequestMapping(value = "/pessoa/deletar/", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Response<Pessoa>> deletar(@RequestParam("id") Long idPessoa) {
		return this.service.delete(idPessoa);
	}
	
	@RequestMapping(value = "/pessoa/pesquisar/", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Response<Pessoa>> pesquisar(@RequestParam("id") Long idPessoa) {
		return this.service.findById(idPessoa);
	}
	
	@RequestMapping(value = "/pessoa/listar", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Response<Page<List<Pessoa>>>> listar(@RequestParam("page") int page,
			@RequestParam("count") int count) {
		return this.service.findAll(page, count);
	}

}
