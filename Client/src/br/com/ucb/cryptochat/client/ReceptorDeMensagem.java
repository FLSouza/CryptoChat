package br.com.ucb.cryptochat.client;

import java.util.Scanner;

public class ReceptorDeMensagem implements Runnable {
	private Scanner entrada;

	private TelaChat telaChat;

	public ReceptorDeMensagem(Scanner entrada, TelaChat telaChat) {
		this.entrada = entrada;
		this.telaChat = telaChat;
	}

	@Override
	public void run() {
		while (this.entrada.hasNextLine()) {
			String mensagem = this.entrada.nextLine();
			this.telaChat.adicionaMensagem(mensagem);
		}
	}

}
