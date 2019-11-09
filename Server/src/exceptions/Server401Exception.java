package exceptions;

import base.ServerResponseCode;

public class Server401Exception extends RuntimeException  {
    public Server401Exception(String msg) {
        super(ServerResponseCode.FAIL.getVALUE() + msg);
    }

    public Server401Exception(ServerExceptionMessage sem) {
        super(ServerResponseCode.FAIL.getVALUE() + sem.getMessage());
    }
}
