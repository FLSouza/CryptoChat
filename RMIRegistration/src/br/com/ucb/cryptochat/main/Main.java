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

    public static synchronized void main(String[] args) {
        try {
            SettingsUtil.setSSLProperty();

            // Configure Secure RMI
            LocateRegistry.createRegistry(Server.RMI_PORT, new SslRMIClientSocketFactory(), new SslRMIServerSocketFactory(null, null, true));
            System.out.println("RMI registry running on port " + Server.RMI_PORT);

            // Blocking the application to terminate itself.
            Main.class.wait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
