package br.com.ucb.cryptochat.model;

import br.com.ucb.cryptochat.util.IOObjectUtil;

import java.io.*;
import java.net.Socket;

/**
 * Created by administrador on 30/05/16.
 */
public class IncomingConnectionHandler extends Thread {

    public static final String EXIT_WORD = "exit";

    private Socket socket;

    private BufferedReader bufferedReader;
    private InputStreamReader inputStreamReader;

    private BufferedWriter bufferedwriter;
    private OutputStreamWriter outputstreamwriter;

    private String buffer = null;

    public IncomingConnectionHandler(Socket socket) throws IOException {
        setSocket(socket);

        InputStream inputstream = System.in;
        this.inputStreamReader = new InputStreamReader(inputstream);
        this.bufferedReader = new BufferedReader(this.inputStreamReader);

        this.outputstreamwriter = new OutputStreamWriter(socket.getOutputStream());
        this.bufferedwriter = new BufferedWriter(outputstreamwriter);
    }

    @Override
    public void run() {
        super.run();
        try {
            while ((this.buffer = this.bufferedReader.readLine()) != null) {
                this.bufferedwriter.write(buffer + '\n');
                this.bufferedwriter.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Getters and Setters */

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

}
