package br.com.ucb.cryptochat.model;

import com.google.gson.Gson;

import javax.net.ssl.SSLSocket;
import java.io.*;
import java.net.Socket;

/**
 * Created by jonathan on 6/9/16.
 */
public class Reader extends Thread implements Serializable {

    private static final long serialVersionUID = -408453013976643597L;

    private String buffer;
    private Socket socket;
    private BufferedReader bufferedReader;

    private InputStreamReader inputStreamReader;

    public Reader(Socket socket) throws IOException {
        this.socket = socket;
        this.inputStreamReader = new InputStreamReader(this.socket.getInputStream());
        this.bufferedReader = new BufferedReader(this.inputStreamReader);
    }

    @Override
    public void run() {
        super.run();
        try {
            while ((this.buffer = this.bufferedReader.readLine()) != null) {
                System.out.println(new Gson().fromJson(this.buffer, Message.class));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
