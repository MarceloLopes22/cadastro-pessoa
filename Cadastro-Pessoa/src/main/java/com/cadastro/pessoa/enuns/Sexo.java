package com.cadastro.pessoa.enuns;

public enum Sexo {

	MASCULINO(1, "Masculino"),FEMININO(2, "Feminino");
	
	private Integer chave;
	private String valor;
	
	Sexo(Integer chave, String valor) {
		this.chave = chave;
		this.valor = valor;
	}
}
