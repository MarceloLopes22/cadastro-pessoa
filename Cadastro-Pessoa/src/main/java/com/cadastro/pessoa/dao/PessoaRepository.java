package com.cadastro.pessoa.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.cadastro.pessoa.basica.Pessoa;

@Repository
public interface PessoaRepository extends PagingAndSortingRepository<Pessoa, Long> {

	Pessoa findPessoaByCpf(String cpf);

}
