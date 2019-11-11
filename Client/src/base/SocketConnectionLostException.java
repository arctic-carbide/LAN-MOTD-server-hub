package base;

import java.net.SocketException;

public class SocketConnectionLostException extends SocketException {

    public SocketConnectionLostException(String s) {
        super(s);
    }

}
