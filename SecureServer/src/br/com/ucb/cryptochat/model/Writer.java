package br.com.ucb.cryptochat.model;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.Socket;

/**
 * Created by jonathan on 6/9/16.
 */
public class Writer extends Thread implements Serializable {

    private String text;

    private Socket socket;
    private BufferedWriter bufferedwriter;
    private OutputStreamWriter outputstreamwriter;

    private static final long serialVersionUID = -169893321782360472L;

    public Writer(String text, Socket socket) throws IOException {
        this.text = text;
        this.socket = socket;
        this.outputstreamwriter = new OutputStreamWriter(this.socket.getOutputStream());
        this.bufferedwriter = new BufferedWriter(this.outputstreamwriter);
    }

    @Override
    public void run() {
        super.run();
        try {
            this.bufferedwriter.write(text + '\n');
            this.bufferedwriter.flush();
            System.out.println("Sent to " + this.socket.getInetAddress() + ": " + this.text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
