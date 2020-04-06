package com.cadastro.pessoa.enuns;

import java.util.Arrays;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Sexo {

	MASCULINO(1, "Masculino"),FEMININO(2, "Feminino");
	
	private Integer chave;
	private String valor;
	
	Sexo(Integer chave, String valor) {
		this.chave = chave;
		this.valor = valor;
	}
	
	public Integer getChave() {
		return chave;
	}

	public void setChave(Integer chave) {
		this.chave = chave;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	@JsonCreator
    public static Sexo setValue(String key) {
    Optional<Sexo> sexo = Arrays.stream(Sexo.values())
            .filter(valor -> valor.toString().equals(key.toUpperCase()))
            .findAny();
    return sexo.orElse(null);
	}
}
