package com.cadastro.pessoa.basica;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import com.cadastro.pessoa.enuns.Sexo;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Pessoa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pessoa_seq")
	@SequenceGenerator(name = "pessoa_seq", sequenceName = "pessoa_seq", allocationSize = 1)
	private Long id;
	
	@NotBlank(message = "Nome é obrigatorio.")
	@Size(message = "Nome não pode ter mais de 200 letras.", max = 200)
	private String nome;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = true)
	private Sexo sexo;
	
	@Email(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Informe um E-mail valido.")
	@Size(max = 200, message = "O E-mail não pode ter mais de 200 letras.")
	@Column(nullable = true)
	private String email;
	
	@NotNull(message = "A data de nascimento deve ser preenchida.")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(columnDefinition = "DATE")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
	private LocalDate data_nascimento;
	
	@Size(max = 200, message = "Naturalidade não pode ter mais de 200 caracteres.")
	@Column(nullable = true)
	private String naturalidade;
	
	@Size(max = 200, message = "Nacionalidade não pode ter mais de 200 caracteres.")
	@Column(nullable = true)
	private String nacionalidade;
	
	@NotBlank(message = "O CPF deve ser preenchido")
	@CPF(message = "Informe um CPF valido.")
	@Size(max = 11, message = "O CPF não pode ter mais de 11 numeros.")
	@Column(nullable = true)
	private String cpf;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date data_cadastro;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date data_atualizacao;
	
	public Pessoa() {
	}

	public Pessoa(
			@NotBlank(message = "Nome é obrigatorio.") @Size(message = "Nome não pode ter mais de 200 letras.", max = 200) String nome,
			Sexo sexo,
			@Email(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Informe um E-mail valido.") @Size(max = 200, message = "O E-mail não pode ter mais de 200 letras.") String email,
			@NotNull(message = "A data de nascimento deve ser preenchida.") LocalDate data_nascimento,
			@Size(max = 200, message = "Naturalidade não pode ter mais de 200 caracteres.") String naturalidade,
			@Size(max = 200, message = "Nacionalidade não pode ter mais de 200 caracteres.") String nacionalidade,
			@NotBlank(message = "O CPF deve ser preenchido") @CPF(message = "Informe um CPF valido.") @Size(max = 11, message = "O CPF não pode ter mais de 11 numeros.") String cpf) {
		setNome(nome);
		setSexo(sexo);
		setEmail(email);
		setData_nascimento(data_nascimento);
		setNaturalidade(naturalidade);
		setNacionalidade(nacionalidade);
		setCpf(cpf);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getData_nascimento() {
		return data_nascimento;
	}

	public void setData_nascimento(LocalDate data_nascimento) {
		this.data_nascimento = data_nascimento;
	}

	public String getNaturalidade() {
		return naturalidade;
	}

	public void setNaturalidade(String naturalidade) {
		this.naturalidade = naturalidade;
	}

	public String getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
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
