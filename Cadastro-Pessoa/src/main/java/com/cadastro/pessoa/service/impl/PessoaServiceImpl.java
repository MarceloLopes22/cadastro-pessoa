package com.cadastro.pessoa.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cadastro.pessoa.basica.Endereco;
import com.cadastro.pessoa.basica.Pessoa;
import com.cadastro.pessoa.constantes.Mensagens;
import com.cadastro.pessoa.controller.response.Response;
import com.cadastro.pessoa.dao.PessoaRepository;
import com.cadastro.pessoa.enuns.Acao;
import com.cadastro.pessoa.service.PessoaService;

@Service
public class PessoaServiceImpl implements PessoaService {
	
	@Autowired
	private PessoaRepository dao;

	@Override
	public ResponseEntity<Response<Pessoa>> create(Pessoa pessoa) {

		Response<Pessoa> response = validarPessoa(pessoa, Acao.INSERIR);

		if (response != null && response.getErros().size() > 0) {
			return new ResponseEntity<Response<Pessoa>>(response, HttpStatus.BAD_REQUEST);
		}

		if (response.getErros().isEmpty()) {
			pessoa.setData_cadastro(new Date());
			Endereco endereco = pessoa.getEndereco();
			endereco.setData_cadastro(new Date());
			pessoa = dao.save(pessoa);
			mensagemSucesso(pessoa, response, Mensagens.PESSOA_CADASTRADA_COM_SUCESSO);
		}

		return new ResponseEntity<Response<Pessoa>>(response, response.getHttpStatus());
	}

	@Override
	public ResponseEntity<Response<Pessoa>> update(Pessoa pessoa) {
		
		Response<Pessoa> response = validarPessoa(pessoa, Acao.ALTERAR);

		if (response != null && response.getErros().size() > 0) {
			return new ResponseEntity<Response<Pessoa>>(response, HttpStatus.BAD_REQUEST);
		}

		if (response.getErros().isEmpty()) {
			pessoa.setData_atualizacao(new Date());
			Endereco endereco = pessoa.getEndereco();
			endereco.setData_atualizacao(new Date());
			pessoa = dao.save(pessoa);
			mensagemSucesso(pessoa, response, Mensagens.PESSOA_ALTERADA_COM_SUCESSO);
		}

		return new ResponseEntity<Response<Pessoa>>(response, response.getHttpStatus());
	}

	@Override
	public ResponseEntity<Response<Pessoa>> delete(Long idPessoa) {
		
		Response<Pessoa> response = validarExclusao(idPessoa);

		if (response != null && response.getErros().size() > 0) {
			return new ResponseEntity<Response<Pessoa>>(response, HttpStatus.BAD_REQUEST);
		}

		if (response.getErros().isEmpty()) {
			Pessoa pessoa = Pessoa.class.cast(response.getData());
			dao.delete(pessoa);
			mensagemSucesso(pessoa, response, Mensagens.PESSOA_EXCLUIDA_COM_SUCESSO);
		}

		return new ResponseEntity<Response<Pessoa>>(response, response.getHttpStatus());
	}

	private Response<Pessoa> validarExclusao(Long idPessoa) {
		Response<Pessoa> response = new Response<>();
		Optional<Pessoa> optional = dao.findById(idPessoa);
		
		if (optional != null && !optional.isPresent()) {
			response.getErros().add(Mensagens.NAO_E_POSSIVEL_APAGAR_UMA_PESSOA_INEXISTENTE);
		} else {
			response.setData(optional.get());
		}
		return response;
	}

	@Override
	public ResponseEntity<Response<Page<List<Pessoa>>>> findAll(int page, int count) {
		Response<Page<List<Pessoa>>> response = new Response<Page<List<Pessoa>>>();
		PageRequest pageable = PageRequest.of(page, count);
		Page<Pessoa> all = dao.findAll(pageable);
		response.setData(all);
		response.setHttpStatus(HttpStatus.OK);
		
		return new ResponseEntity<Response<Page<List<Pessoa>>>>(response, response.getHttpStatus());
	}

	@Override
	public ResponseEntity<Response<Pessoa>> findById(Long idPessoa) {
		Response<Pessoa> response = new Response<>();
		Optional<Pessoa> optional = dao.findById(idPessoa);

		if (optional != null && !optional.isPresent()) {
			response.getErros().add(Mensagens.PESSOA_NAO_ENCONTRADA);
			response.setHttpStatus(HttpStatus.BAD_REQUEST);
			response.setData(null);
		} else {
			Pessoa pessoa = optional.get();
			response.setData(pessoa);
			mensagemSucesso(pessoa, response, Mensagens.PESSOA_ENCONTRADA);
		}
		
		return new ResponseEntity<Response<Pessoa>>(response, response.getHttpStatus());
	}

	private void mensagemSucesso(Pessoa pessoa, Response<Pessoa> response, String mensagem) {
		response.setData(pessoa);
		response.setHttpStatus(HttpStatus.OK);
		response.setMensagemSucesso(mensagem);
	}

	private Response<Pessoa> validarPessoa(Pessoa pessoa, Acao acao) {
		final Response<Pessoa> response = new Response<Pessoa>();
		final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		final Set<ConstraintViolation<Pessoa>> violations = validator.validate(pessoa);;
		

		if (violations != null && !violations.isEmpty()) {
			violations.forEach(e -> response.getErros().add(e.getMessage()));
		}
		Endereco endereco = pessoa.getEndereco();
		if (endereco != null && endereco.getCep().length() != 8) {
			response.getErros().add(Mensagens.CEP_INVALIDO);
		}

		if (Acao.INSERIR.equals(acao)) {
			Pessoa pessoaByCpf = dao.findPessoaByCpf(pessoa.getCpf());
			if (pessoaByCpf != null) {
				response.getErros().add(Mensagens.JA_EXISTE_UMA_PESSOA_CADASTRADA_COM_ESSE_CPF_POR_FAVOR_TENTE_OUTRO);
			}
		}

		return response;
	}

	@Override
	public Pessoa findByCpf(String cpf) {
		return dao.findPessoaByCpf(cpf);
	}

}
