package br.com.ucb.cryptochat.main;

import br.com.ucb.cryptochat.interfaces.Server;
import br.com.ucb.cryptochat.model.Client;
import br.com.ucb.cryptochat.model.IncomingConnectionHandler;
import br.com.ucb.cryptochat.services.ServerImplementation;
import br.com.ucb.cryptochat.util.SettingsUtil;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.rmi.ssl.SslRMIClientSocketFactory;
import java.io.*;
import java.net.ServerSocket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by jonathan on 6/8/16.
 */
public class Main {

    public static void main(String[] args) {
        try {
            SettingsUtil.setSSLProperty();

            // Configure Socket
            SSLServerSocketFactory factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            SSLServerSocket serverSocket = (SSLServerSocket) factory.createServerSocket(Server.PORT);

            // Configure RMI
            Registry registry = LocateRegistry.getRegistry(null, Server.RMI_PORT, new SslRMIClientSocketFactory());
            ServerImplementation serverImplementation = ServerImplementation.getInstance();
            registry.bind(Server.class.getSimpleName(), serverImplementation);

            while (true) {
                SSLSocket socket = (SSLSocket) serverSocket.accept();
                Client client = new Client(socket.getInetAddress(), socket.getPort());
                serverImplementation.addClient(client, socket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
