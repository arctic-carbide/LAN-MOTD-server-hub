package shared;

import java.io.*;
import java.net.Socket;

public class ClientMemberData {
    public Socket socket;
    public BufferedReader istream;
    public PrintStream ostream;

    public ClientMemberData() {}

    public ClientMemberData(ClientMemberData source) {
        socket = source.socket;
        istream = source.istream;
        ostream = source.ostream;
    }

    public ClientMemberData(String IP) throws Exception {
        socket = new Socket(IP, CommunicationData.SERVER_PORT);
        acquireSocketStreams();
    }

    protected void acquireSocketStreams() throws Exception {
        istream = adaptStream(socket.getInputStream());
        ostream = adaptStream(socket.getOutputStream());
    }

    private BufferedReader adaptStream(InputStream is) {
        InputStreamReader isr = new InputStreamReader(is);
        return new BufferedReader(isr);
    }

    private PrintStream adaptStream(OutputStream os) {
        return new PrintStream(os);
    }

}
