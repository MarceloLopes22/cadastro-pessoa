package com.cadastro.pessoa.basica;

public class Autenticador {
	private String mensagem;

	public Autenticador(String message) {
		this.mensagem = message;
	}

	public String getMessagem() {
		return mensagem;
	}

	public void setMessagem(String mensagem) {
		this.mensagem = mensagem;
	}

	@Override
	public String toString() {
		return String.format("Olá mundo, [message=%s]", mensagem);
	}
}
