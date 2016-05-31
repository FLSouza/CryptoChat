package br.com.ucb.cryptochat.client;

import java.io.PrintStream;

public class EmissorDeMensagem {
	private PrintStream saida;

	public EmissorDeMensagem(PrintStream saida) {
		this.saida = saida;
	}

	public void envia(String mensagem) {
		this.saida.println(mensagem);
	}
}
