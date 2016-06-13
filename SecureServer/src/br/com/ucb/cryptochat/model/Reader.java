package br.com.ucb.cryptochat.model;

import com.google.gson.Gson;

import javax.net.ssl.SSLSocket;
import java.io.*;
import java.net.Socket;
import java.util.Collections;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by jonathan on 6/9/16.
 */
public class Reader extends Thread implements Serializable {

    private static final long serialVersionUID = -408453013976643597L;

    private String buffer;
    private Socket socket;
    private BufferedReader bufferedReader;

    private NotificationCenter notificationCenter;

    private InputStreamReader inputStreamReader;

    private Reader() {
        this.notificationCenter = new NotificationCenter();
    }

    public Reader(Socket socket) throws IOException {
        this();
        this.socket = socket;
        this.inputStreamReader = new InputStreamReader(this.socket.getInputStream());
        this.bufferedReader = new BufferedReader(this.inputStreamReader);
    }

    public void addListener(Observer observer) {
        if (observer != null) {
            this.notificationCenter.addObserver(observer);
        }
    }

    @Override
    public void run() {
        super.run();
        try {
            while ((this.buffer = this.bufferedReader.readLine()) != null) {
                Message message = new Gson().fromJson(this.buffer, Message.class);
                System.out.println(message);
                this.notificationCenter.notify(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* Notification Center */

    public static class NotificationCenter extends Observable {

        public void notify(Message message) {
            setChanged();
            notifyObservers(message);
        }
    }
}
