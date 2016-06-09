package br.com.ucb.cryptochat.main;

import br.com.ucb.cryptochat.interfaces.Server;
import br.com.ucb.cryptochat.model.Message;
import br.com.ucb.cryptochat.model.Reader;
import br.com.ucb.cryptochat.util.SettingsUtil;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.rmi.ssl.SslRMIClientSocketFactory;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class Client {

    public static void main(String[] args) throws Exception {

        SettingsUtil.setSSLProperty();

        SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket sslsocket = (SSLSocket) sslsocketfactory.createSocket("localhost", Server.PORT);

        Reader reader = new Reader(sslsocket);
        reader.start();

        br.com.ucb.cryptochat.model.Client client = new br.com.ucb.cryptochat.model.Client(sslsocket.getInetAddress(), sslsocket.getLocalPort(), "jonathan");

        Registry registry = LocateRegistry.getRegistry(null, Server.RMI_PORT, new SslRMIClientSocketFactory());
        Server server = (Server) registry.lookup(Server.class.getSimpleName());

        List<br.com.ucb.cryptochat.model.Client> clients = server.getsClients();

        System.out.println("Clients: " + clients);

        server.sendMessage(new Message("Olá", client, client));

        Thread.sleep(1000);

        server.sendMessage(new Message("Olá", client, client));

        /*
        Socket socket = new Socket("127.0.0.1", 10000);

		PrintStream saida = new PrintStream(socket.getOutputStream());

		Scanner entrada = new Scanner(socket.getInputStream());

		EmissorDeMensagem emissor = new EmissorDeMensagem(saida);

		TelaChat telaChat = new TelaChat(emissor);

		ReceptorDeMensagem receptor = new ReceptorDeMensagem(entrada, telaChat);

		Thread pilha = new Thread(receptor);
		pilha.start();
		*/
    }
}
