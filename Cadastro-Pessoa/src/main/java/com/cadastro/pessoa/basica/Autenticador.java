package com.cadastro.pessoa.basica;

public class Autenticador {
	private String mensagem;

	public Autenticador(String message) {
		setMessagem(message);
	}

	public String getMessagem() {
		return mensagem;
	}

	public void setMessagem(String mensagem) {
		this.mensagem = mensagem;
	}
}
