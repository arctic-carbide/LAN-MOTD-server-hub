package shared;

import java.net.ServerSocket;

public class ServerMemberData extends ClientMemberData {
    private ServerSocket serverSocket;

    public ServerMemberData() throws Exception {
        serverSocket = new ServerSocket(CommunicationData.SERVER_PORT);
    }

    public ServerMemberData(ServerMemberData source) {
        super(source);
        serverSocket = source.serverSocket;
    }

    public void establishConnection() throws Exception {
        socket = serverSocket.accept(); // waits and listens for a connection on the socket
        acquireSocketStreams();
    }
}
