package commands;

import shared.ServerResponseCode;
import shared.*;

public class MessageGetCommand extends AnonymousUserCommand {

    public void call() {
        forwardMessageToClient();
    }

    private void forwardMessageToClient() {
        Utility.display("Starting MSGGET procedure...");

        server.getOS().println(ServerResponseCode.OK.VALUE);
        server.setMOTD(server.getMessageQueue().next()); // cycle through to the next message
        server.getOS().println(server.getMOTD());

        Utility.display("Procedure success!");
    }

}
