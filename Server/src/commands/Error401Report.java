package commands;

import base.ServerResponseCode;

public class Error401Report extends ServerCommand {
    private String errorMessage;
    public Error401Report(String msg) { errorMessage = msg; }

    public void call() {
        System.err.println(ServerResponseCode.FAIL + errorMessage);
    }

}
