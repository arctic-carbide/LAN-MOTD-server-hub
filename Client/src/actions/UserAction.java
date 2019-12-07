package actions;

import base.Client;
import shared.CommandName;

public abstract class UserAction {
    protected Client caller;
    protected String serverResponse;

    protected  UserAction() {}

    protected UserAction(Client c, String r) {
        caller = c;
        serverResponse = r;
    }

    public static UserAction determineAction(Client caller, String[] partitionedCommandLine) {
        CommandName command = CommandName.valueOf(partitionedCommandLine[0]);

        switch (command) {
            // case MSGGET: return new MessageGetAction(caller); // no longer needed, with an response monitor
            case MSGSTORE: return new MessageStoreAction(caller);
            case QUIT: return new QuitAction(caller);
            case SEND: return new SendAction(caller, partitionedCommandLine[1]);
            default: return new NoAction();
        }
    }

    public void execute() {
        try {
//            serverResponse = caller.retrieveServerResponse();
//            System.out.println(serverResponse);

            hook();
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }

    public boolean isQuit() { return this instanceof QuitAction; }

    protected abstract void hook() throws Exception;
}
