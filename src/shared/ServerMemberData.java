package shared;

import java.net.ServerSocket;

public class ServerMemberData extends Node {

    public ServerSocket serverSocket;

    public ServerMemberData() throws Exception {
        serverSocket = new ServerSocket(SOCKET_PORT);
    }

    public ServerMemberData(ServerMemberData source) {
        super(source);
        serverSocket = source.serverSocket;
    }

    public void listen() throws Exception {
        socket = serverSocket.accept();
        acquireSocketStreams();
    }
}
