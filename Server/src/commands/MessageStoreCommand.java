package commands;

import base.ServerResponseCode;
import shared.Utility;

public class MessageStoreCommand extends ServerCommand {

    public void call() {
        try {
            storeClientMessage();
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void storeClientMessage() throws Exception {
        String message = null;

        Utility.display("Starting MSGSTORE procedure...");
        // System.out.println("Starting MSGSTORE procedure...");

        // if (!rootUser) {
        if (!reference.isUserLoggedIn()) { // nobody is logged in
            Utility.display("Procedure MSGSTORE aborted!");
            reference.getOS().println(ServerResponseCode.FAIL + "You are not currently logged in, log in first");
            return;
        }
        else {
            reference.getOS().println(ServerResponseCode.OK);
        }

        Utility.display("Waiting for user message...");
        message = reference.getIS().readLine();

        // try to add the message to the server
        // the queue will check to see if it can fit the message in its internal structure
        // if it can, it will add it and return true
        // else, if doesn't and returns false

        Utility.display("Try to store user message into server...");
        // System.out.println("Try to store user message into server...");
        if (reference.getMessageQueue().offer(message)) {
            reference.getOfstream().write(message); // append that message to the external file
            reference.getOfstream().newLine();
            // ofstream.flush();
            Utility.display("Procedure MSGSTORE complete!");
            reference.getOS().println(ServerResponseCode.OK); // confirm that the message was added
            // System.out.println("Procedure success!");
        }
        else {
            reference.getOS().println(ServerResponseCode.FAIL + "Too Many Messages in Memory: " + reference.MESSAGE_LIMIT); // confirm that adding the message failed
            Utility.display("Procedure MSGSTORE failed!");
            // System.out.println("Procedure failed!");
        }
    }

}
