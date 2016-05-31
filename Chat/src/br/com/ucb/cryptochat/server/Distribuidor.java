package br.com.ucb.cryptochat.server;

import java.util.ArrayList;
import java.util.Collection;

public class Distribuidor {
	private Collection<Emissor> emissores = new ArrayList<Emissor>();

	public void adicionaEmissor(Emissor emissor) {
		this.emissores.add(emissor);
	}

	public void distribuiMensagem(String mensagem) {
		for (Emissor emissor : this.emissores) {
			emissor.envia(mensagem);
		}
	}

}
