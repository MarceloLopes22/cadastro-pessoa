package com.cadastro.pessoa.basica;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.cadastro.pessoa.enuns.Uf;

@Entity
public class Endereco implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "endereco_seq")
	@SequenceGenerator(name = "endereco_seq", sequenceName = "endereco_seq", allocationSize = 1)
	private Long id;
	
	@NotBlank(message = "O logradouro é obrigatório.")
	@Size(message = "logradouro não pode ter mais de 100 caracteres.", max = 100)
	private String logradouro;
	
	@NotBlank(message = "O bairro é obrigatório.")
	@Size(message = "bairro não pode ter mais de 100 caracteres.", max = 100)
	private String bairro;
	
	@NotBlank(message = "Cidade é obrigatória.")
	@Size(message = "cidade não pode ter mais de 100 caracteres.", max = 100)
	private String cidade;
	
	@NotBlank(message = "Cidade é obrigatória.")
	@Size(message = "cep não pode ter mais de 8 caracteres.", max = 8)
	private String cep;
	
	@NotNull(message = "UF é obrigatório.")
	@Enumerated(EnumType.ORDINAL)
	private Uf uf;
	
	@NotNull(message = "O numero é obrigatório.")
	private int numero;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date data_cadastro;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date data_atualizacao;
	
	public Endereco() {
	}
	
	public Endereco(String longradouro, String bairro, String cidade, String cep, Uf uf, int numero) {
		setLogradouro(longradouro);
		setBairro(bairro);
		setCidade(cidade);
		setCep(cep);
		setUf(uf);
		setNumero(numero);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Uf getUf() {
		return uf;
	}

	public void setUf(Uf uf) {
		this.uf = uf;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Date getData_cadastro() {
		return data_cadastro;
	}

	public void setData_cadastro(Date data_cadastro) {
		this.data_cadastro = data_cadastro;
	}

	public Date getData_atualizacao() {
		return data_atualizacao;
	}

	public void setData_atualizacao(Date data_atualizacao) {
		this.data_atualizacao = data_atualizacao;
	}
}
