package shared;

import jdk.nashorn.internal.objects.annotations.Getter;

import java.io.*;
import java.net.Socket;

public abstract class Node {

    public static final int SOCKET_PORT = 7243;
    protected Socket socket;
    protected BufferedReader socketIStream;
    protected PrintStream socketOStream;

    public Socket getSocket() { return socket; }

    protected Node() {}

    protected Node(Node source) {
        socket = source.socket;
        socketIStream = source.socketIStream;
        socketOStream = source.socketOStream;
    }

    public void acquireSocketStreams() throws Exception {
        socketIStream = wrapStream(socket.getInputStream());
        socketOStream = wrapStream(socket.getOutputStream());
    }

    private BufferedReader wrapStream(InputStream is) {
        InputStreamReader isr = new InputStreamReader(is);
        return new BufferedReader(isr);
    }

    private PrintStream wrapStream(OutputStream os) {
        return new PrintStream(os);
    }
}
