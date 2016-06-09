package br.com.ucb.cryptochat.util;

import java.io.*;
import java.net.Socket;

/**
 * Created by jonathan on 6/9/16.
 */
public class IOObjectUtil {

    public static BufferedReader getReader(Socket socket) throws IOException {
        InputStreamReader inputstreamreader = new InputStreamReader(socket.getInputStream());
        return new BufferedReader(inputstreamreader);
    }

    public static BufferedWriter getWriter(Socket socket) throws IOException {
        OutputStreamWriter outputstreamwriter = new OutputStreamWriter(socket.getOutputStream());
        return new BufferedWriter(outputstreamwriter);
    }
}
