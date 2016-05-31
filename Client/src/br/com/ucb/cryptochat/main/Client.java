package br.com.ucb.cryptochat.main;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import br.com.ucb.cryptochat.client.EmissorDeMensagem;
import br.com.ucb.cryptochat.client.ReceptorDeMensagem;
import br.com.ucb.cryptochat.client.TelaChat;

public class Client {
	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket socket = new Socket("127.0.0.1", 10000);

		PrintStream saida = new PrintStream(socket.getOutputStream());

		Scanner entrada = new Scanner(socket.getInputStream());

		EmissorDeMensagem emissor = new EmissorDeMensagem(saida);

		TelaChat telaChat = new TelaChat(emissor);

		ReceptorDeMensagem receptor = new ReceptorDeMensagem(entrada, telaChat);

		Thread pilha = new Thread(receptor);
		pilha.start();
	}
}
