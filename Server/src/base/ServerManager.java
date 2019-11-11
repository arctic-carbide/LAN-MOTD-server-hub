package base;

import com.sun.security.ntlm.Server;
import shared.Node;
import shared.ServerMemberData;

import java.net.ServerSocket;

// Allocates threads for each client connection to a server instance
public class ServerManager extends Node {

    // SINGLETON BLOCK
    private static ServerManager instance;
    private ServerSocket serverSocket;
    // private ServerMemberData serverData;

    private ServerManager() throws Exception {
        serverSocket = new ServerSocket(SOCKET_PORT);
        ServerInstance.init();
    }

    public static ServerManager getInstance() throws Exception {
         if (instance == null) {
             instance = new ServerManager();
         }

         return instance;
    }
    // END BLOCK

    private void listen() throws Exception {
        socket = serverSocket.accept(); // waits and listens for a connection on the socket
        acquireSocketStreams();
    }

    public void start() {
        ServerInstance currentInstance;
        Thread thread;

        while (true) {
            try {
                listen();

                currentInstance = new ServerInstance(this);
                thread = new Thread(currentInstance);

                thread.start();
            }
            catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
