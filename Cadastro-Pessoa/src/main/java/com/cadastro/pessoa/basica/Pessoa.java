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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((data_atualizacao == null) ? 0 : data_atualizacao.hashCode());
		result = prime * result + ((data_cadastro == null) ? 0 : data_cadastro.hashCode());
		result = prime * result + ((data_nascimento == null) ? 0 : data_nascimento.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nacionalidade == null) ? 0 : nacionalidade.hashCode());
		result = prime * result + ((naturalidade == null) ? 0 : naturalidade.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((sexo == null) ? 0 : sexo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pessoa other = (Pessoa) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (data_atualizacao == null) {
			if (other.data_atualizacao != null)
				return false;
		} else if (!data_atualizacao.equals(other.data_atualizacao))
			return false;
		if (data_cadastro == null) {
			if (other.data_cadastro != null)
				return false;
		} else if (!data_cadastro.equals(other.data_cadastro))
			return false;
		if (data_nascimento == null) {
			if (other.data_nascimento != null)
				return false;
		} else if (!data_nascimento.equals(other.data_nascimento))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nacionalidade == null) {
			if (other.nacionalidade != null)
				return false;
		} else if (!nacionalidade.equals(other.nacionalidade))
			return false;
		if (naturalidade == null) {
			if (other.naturalidade != null)
				return false;
		} else if (!naturalidade.equals(other.naturalidade))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (sexo != other.sexo)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pessoa [id=" + id + ", nome=" + nome + ", sexo=" + sexo + ", email=" + email + ", data_nascimento="
				+ data_nascimento + ", naturalidade=" + naturalidade + ", nacionalidade=" + nacionalidade + ", cpf="
				+ cpf + ", data_cadastro=" + data_cadastro + ", data_atualizacao=" + data_atualizacao + "]";
	}
	
	
}
