package br.com.ucb.cryptochat.main;

import java.io.IOException;
import java.net.ServerSocket;

import br.com.ucb.cryptochat.server.Distribuidor;
import br.com.ucb.cryptochat.server.Registrador;

public class Server {
	public static void main(String[] args) throws IOException {
		Distribuidor distribuidor = new Distribuidor();

		ServerSocket serverSocket = new ServerSocket(10000);

		Registrador registrador = new Registrador(distribuidor, serverSocket);
		Thread pilha = new Thread(registrador);
		pilha.start();
	}
}
