package com.cadastro.pessoa.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.cadastro.pessoa.basica.Pessoa;
import com.cadastro.pessoa.controller.response.Response;

public interface PessoaService {
	
	public ResponseEntity<Response<Pessoa>> create(Pessoa pessoa);

	public ResponseEntity<Response<Pessoa>> update(Pessoa pessoa);

	public ResponseEntity<Response<Pessoa>> delete(Long idPessoa);

	public ResponseEntity<Response<Page<List<Pessoa>>>> findAll(int page, int count);

	public ResponseEntity<Response<Pessoa>> findById(Long idPessoa);

}
