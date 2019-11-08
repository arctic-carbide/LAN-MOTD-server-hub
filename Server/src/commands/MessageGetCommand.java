package commands;

import base.ServerResponseCode;
import shared.*;

public class MessageGetCommand extends ServerCommand {

    public void call() {
        forwardMessageToClient();
    }

    private void forwardMessageToClient() {
        Utility.display("Starting MSGGET procedure...");

        reference.getOS().println(ServerResponseCode.OK);
        reference.setMOTD(reference.getMessageQueue().next()); // cycle through to the next message
        reference.getOS().println(reference.getMOTD());

        Utility.display("Procedure success!");
    }

}
