package br.com.ucb.cryptochat.services;

import br.com.ucb.cryptochat.interfaces.Server;
import br.com.ucb.cryptochat.model.Client;
import br.com.ucb.cryptochat.model.Message;
import br.com.ucb.cryptochat.model.Writer;
import com.google.gson.Gson;

import javax.rmi.ssl.SslRMIClientSocketFactory;
import javax.rmi.ssl.SslRMIServerSocketFactory;
import java.io.IOException;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by jonathan on 6/8/16.
 */
public class ServerImplementation extends UnicastRemoteObject implements Server {

    private static ServerImplementation serverImplementation = null;

    private Map<Client, Socket> clients;

    public static ServerImplementation getInstance() {
        if (serverImplementation == null) {
            try {
                serverImplementation = new ServerImplementation();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return serverImplementation;
    }

    private ServerImplementation() throws RemoteException {
        super(0, new SslRMIClientSocketFactory(), new SslRMIServerSocketFactory(null, null, true));
        this.clients = new LinkedHashMap<>();
    }

    public void addClient(Client client, Socket socket) {
        this.clients.put(client, socket);
    }

    @Override
    public List<Client> getsClients() throws RemoteException {
        return new LinkedList<>(this.clients.keySet());
    }

    @Override
    public Boolean sendMessage(Message message) throws RemoteException {
        System.out.println("Trying to send: " + message.toString());
        if (this.clients.containsKey(message.getRecipient())) {
            try {
                Writer writer = new Writer(new Gson().toJson(message).toString(), this.clients.get(message.getRecipient()));
                writer.start();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
                return false;
            }
        } else {
            System.out.println(message.getRecipient().getAddress() + "not logged.");
            return false;
        }
    }

    @Override
    public Client updateUsername(Client client, String newUsername) {
        if (this.clients.containsKey(client)) {
            Client clientToUpdate = getClient(client);
            clientToUpdate.setUsername(newUsername);
            return clientToUpdate;
        }
        return null;
    }

    public Client getClient(Client client) {
        List<Client> clients = new LinkedList<>(this.clients.keySet());
        int indexOfClient = clients.indexOf(client);
        if (indexOfClient == -1) {
            return null;
        } else {
            return clients.get(indexOfClient);
        }
    }
}
