package commands;

import shared.*;

public class MessageGetCommand extends ServerCommand {

    public void call() {

    }

    private void GetMessage() {
        Utility.Display("Starting MSGGET procedure...");

        os.println(OK_MESSAGE);
        messageOfTheDay = messages.next(); // cycle through to the next message
        os.println(messageOfTheDay);

        Display("Procedure success!");
    }

}
