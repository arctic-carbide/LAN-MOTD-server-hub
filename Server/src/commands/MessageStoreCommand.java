package commands;

import base.ServerInstance;
import shared.ServerResponseCode;
import shared.Utility;

public class MessageStoreCommand extends ServerCommand {

    @Override
    public void call() throws Exception {
        storeClientMessage();
    }

    private void storeClientMessage() throws Exception {
        String message;

        Utility.display("Starting MSGSTORE procedure...");
        // System.out.println("Starting MSGSTORE procedure...");

        // if (!rootUser) {
        if (!server.isUserLoggedIn()) { // nobody is logged in
            Utility.display("Procedure MSGSTORE aborted!");
            server.getOS().println(ServerResponseCode.FAIL + "You are not currently logged in, log in first");
            return;
        }
        else {
            server.getOS().println(ServerResponseCode.OK.VALUE);
        }

        Utility.display("Waiting for user message...");
        message = server.getIS().readLine();

        // try to add the message to the server
        // the queue will check to see if it can fit the message in its internal structure
        // if it can, it will add it and return true
        // else, if doesn't and returns false

        Utility.display("Try to store user message into server...");
        if (server.getMessageQueue().offer(message)) {
            server.getOutputFileStream().write(message); // append that message to the external file
            server.getOutputFileStream().newLine();
            // ofstream.flush();
            Utility.display("Procedure MSGSTORE complete!");
            server.getOS().println(ServerResponseCode.OK.VALUE); // confirm that the message was added
            // System.out.println("Procedure success!");
        }
        else {
            server.getOS().println(ServerResponseCode.FAIL + "Too Many Messages in Memory: " + ServerInstance.MESSAGE_LIMIT); // confirm that adding the message failed
            Utility.display("Procedure MSGSTORE failed!");
            // System.out.println("Procedure failed!");
        }
    }

}
