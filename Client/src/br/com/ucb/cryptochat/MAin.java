package br.com.ucb.cryptochat;

import br.com.ucb.cryptochat.interfaces.Server;
import br.com.ucb.cryptochat.model.Client;
import br.com.ucb.cryptochat.model.Reader;
import br.com.ucb.cryptochat.util.SettingsUtil;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.rmi.ssl.SslRMIClientSocketFactory;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {

    private static final String DEFAULT_HOST = "localhost";

    public static void main(String[] args) throws Exception {

        String serverAddress = args.length >= 1 ? args[0] : DEFAULT_HOST;
        String username = args.length >= 2 ? args[1] : null;

        // Initialize security properties
        SettingsUtil.setSSLProperty();

        // Configure Client Secure Socket
        SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket sslsocket = (SSLSocket) sslsocketfactory.createSocket(serverAddress, Server.PORT);

        // Start Job for listening incoming Messages
        Reader reader = new Reader(sslsocket);
        reader.start();

        // Create a instance for local Client with a username. This should done in the interface using @{ServerImplementation#updateUsername} method.
        Client client = new Client(sslsocket.getInetAddress(), sslsocket.getLocalPort(), username);

        // Configure Secure RMI
        Registry registry = LocateRegistry.getRegistry(null, Server.RMI_PORT, new SslRMIClientSocketFactory());
        Server server = (Server) registry.lookup("rmi://crypto_chat");

        if (username != null) {
            server.updateUsername(client, username);
        }

        ChatScreen chatScreen = new ChatScreen(sslsocket, client, server);
        reader.addListener(chatScreen);
    }
}
