package shared;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientMemberData extends Node {

    public BufferedReader stdInput;

    public ClientMemberData() {
        init();
    }

    public ClientMemberData(ClientMemberData source) {
        super(source);
        init();
    }

    public ClientMemberData(String IP) throws Exception {
        establishConnection(IP);
    }

    public void establishConnection(String IP) throws Exception {
        socket = new Socket(IP, SOCKET_PORT);
        acquireSocketStreams();
    }

    public boolean isConnected() {
        boolean a = socket != null;
        boolean b = socketIStream != null;
        boolean c = socketOStream != null;

        return a && b && c;
    }

    private void init() {
        stdInput = new BufferedReader(new InputStreamReader(System.in));
    }

}
