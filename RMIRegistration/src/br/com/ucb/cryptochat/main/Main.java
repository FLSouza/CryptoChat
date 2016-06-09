package br.com.ucb.cryptochat.main;

import br.com.ucb.cryptochat.interfaces.Server;
import br.com.ucb.cryptochat.util.SettingsUtil;

import javax.rmi.ssl.SslRMIClientSocketFactory;
import javax.rmi.ssl.SslRMIServerSocketFactory;
import java.rmi.registry.LocateRegistry;

/**
 * Created by jonathan on 6/8/16.
 */
public class Main {

    public static void main(String[] args) {
        try {
            SettingsUtil.setSSLProperty();

            LocateRegistry.createRegistry(Server.RMI_PORT, new SslRMIClientSocketFactory(), new SslRMIServerSocketFactory(null, null, true));
            System.out.println("RMI registry running on port " + Server.RMI_PORT);
            while (true) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
