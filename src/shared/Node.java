package shared;

import java.io.*;
import java.net.Socket;

public abstract class Node {

    public static final int SOCKET_PORT = 7243;
    protected Socket socket;
    protected BufferedReader socketIStream;
    protected PrintStream socketOStream;

    protected Node() {}

    protected Node(Node source) {
        socket = source.socket;
        socketIStream = source.socketIStream;
        socketOStream = source.socketOStream;
    }

    public void acquireSocketStreams() throws Exception {
        socketIStream = adaptStream(socket.getInputStream());
        socketOStream = adaptStream(socket.getOutputStream());
    }

    private BufferedReader adaptStream(InputStream is) {
        InputStreamReader isr = new InputStreamReader(is);
        return new BufferedReader(isr);
    }

    private PrintStream adaptStream(OutputStream os) {
        return new PrintStream(os);
    }
}
