package exceptions;

import shared.ServerResponseCode;

public class Server401Exception extends RuntimeException  {
    public Server401Exception(String msg) {
        super(ServerResponseCode.FAIL.VALUE + msg);
    }

    public Server401Exception(ServerExceptionMessage sem) {
        super(ServerResponseCode.FAIL.VALUE + sem.MESSAGE);
    }
}
