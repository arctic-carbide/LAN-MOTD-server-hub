package shared;

import java.net.ServerSocket;

public class MessageReceiver extends Node implements Runnable {
    private ServerSocket serverSocket;

    @Override
    public void run() {
        try {
            socket = serverSocket.accept();
            acquireSocketStreams();

        }
        catch (Exception e) {
            System.err.println(e);
        }
    }

    private void listen() throws Exception {
        while (true) {
            System.out.println(socketIStream.readLine()); // blocks if there's nothing
        }
    }

    private void acquireConnection() {

    }
}
