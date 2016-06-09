package br.com.ucb.cryptochat.interfaces;

import br.com.ucb.cryptochat.model.Client;
import br.com.ucb.cryptochat.model.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by jonathan on 6/8/16.
 */
public interface Server extends Remote {

    public static final Boolean SECURE_CHAT = true;

    public static final Integer PORT = 2323;
    public static final Integer RMI_PORT = 2324;

    public List<Client> getsClients() throws RemoteException;

    public Boolean sendMessage(Message message) throws RemoteException;

    public Client updateUsername(Client client, String newUsername) throws RemoteException;

}
