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

import com.cadastro.pessoa.constantes.Mensagens;
import com.cadastro.pessoa.enuns.Sexo;
import com.cadastro.pessoa.enuns.Uf;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Pessoa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pessoa_seq")
	@SequenceGenerator(name = "pessoa_seq", sequenceName = "pessoa_seq", allocationSize = 1)
	private Long id;
	
	@NotBlank(message = Mensagens.NOME_E_OBRIGATORIO)
	@Size(message = Mensagens.NOME_NAO_PODE_TER_MAIS_DE_200_LETRAS, max = 200)
	private String nome;
	
	@Enumerated(EnumType.ORDINAL)
	private Sexo sexo;
	
	@Email(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = Mensagens.INFORME_UM_E_MAIL_VALIDO)
	@Size(max = 200, message = Mensagens.O_E_MAIL_NAO_PODE_TER_MAIS_DE_200_LETRAS)
	private String email;
	
	@NotNull(message = Mensagens.A_DATA_DE_NASCIMENTO_DEVE_SER_PREENCHIDA)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(columnDefinition = "DATE")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
	private LocalDate data_nascimento;
	
	@Size(max = 200, message = Mensagens.NATURALIDADE_NAO_PODE_TER_MAIS_DE_200_CARACTERES)
	@Column(nullable = true)
	private String naturalidade;
	
	@Size(max = 200, message = Mensagens.NACIONALIDADE_NAO_PODE_TER_MAIS_DE_200_CARACTERES)
	@Column(nullable = true)
	private String nacionalidade;
	
	@NotBlank(message = Mensagens.O_CPF_DEVE_SER_PREENCHIDO)
	@CPF(message = Mensagens.INFORME_UM_CPF_VALIDO)
	@Size(max = 11, message = Mensagens.O_CPF_NAO_PODE_TER_MAIS_DE_11_NUMEROS)
	private String cpf;
	
	@NotBlank(message = Mensagens.O_LOGRADOURO_E_OBRIGATORIO)
	@Size(message = Mensagens.LOGRADOURO_NAO_PODE_TER_MAIS_DE_100_CARACTERES, max = 100)
	private String logradouro;
	
	@NotBlank(message = Mensagens.O_BAIRRO_E_OBRIGATORIO)
	@Size(message = Mensagens.BAIRRO_NAO_PODE_TER_MAIS_DE_100_CARACTERES, max = 100)
	private String bairro;
	
	@NotBlank(message = Mensagens.CIDADE_E_OBRIGATORIA)
	@Size(message = Mensagens.CIDADE_NAO_PODE_TER_MAIS_DE_100_CARACTERES, max = 100)
	private String cidade;
	
	@NotBlank(message = Mensagens.CEP_E_OBRIGATORIO)
	@Size(message = Mensagens.CEP_NAO_PODE_TER_MAIS_DE_8_CARACTERES, max = 8)
	private String cep;
	
	@NotNull(message = Mensagens.UF_E_OBRIGATORIO)
	@Enumerated(EnumType.ORDINAL)
	private Uf uf;
	
	@NotNull(message = Mensagens.O_NUMERO_E_OBRIGATORIO)
	private Integer numero;
	
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
			@NotBlank(message = "O CPF deve ser preenchido") @CPF(message = "Informe um CPF valido.") @Size(max = 11, message = "O CPF não pode ter mais de 11 numeros.") String cpf,
			@NotBlank(message = "O logradouro é obrigatório.") @Size(message = "logradouro não pode ter mais de 100 caracteres.", max = 100) String logradouro,
			@NotBlank(message = "O bairro é obrigatório.") @Size(message = "bairro não pode ter mais de 100 caracteres.", max = 100) String bairro,
			@NotBlank(message = "Cidade é obrigatória.") @Size(message = "cidade não pode ter mais de 100 caracteres.", max = 100) String cidade,
			@NotBlank(message = "Cidade é obrigatória.") @Size(message = "cep não pode ter mais de 8 caracteres.", max = 8) String cep,
			@NotNull(message = "UF é obrigatório.") Uf uf, @NotNull(message = "O numero é obrigatório.") Integer numero) {
		setNome(nome);
		setSexo(sexo);
		setEmail(email);
		setData_nascimento(data_nascimento);
		setNaturalidade(naturalidade);
		setNacionalidade(nacionalidade);
		setCpf(cpf);
		setLogradouro(logradouro);
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

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
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
