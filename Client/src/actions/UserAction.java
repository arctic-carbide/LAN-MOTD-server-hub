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

    public static UserAction determineAction(Client caller, String userCommand) {
        CommandName command = CommandName.valueOf(userCommand);

        switch (command) {
            case MSGGET: return new MessageGetAction(caller);
            case MSGSTORE: return new MessageStoreAction(caller);
            case QUIT: return new QuitAction(caller);
            default: return new NoAction();
        }
    }

    public void execute() {
        try {
            serverResponse = caller.retrieveServerResponse();
            System.out.println(serverResponse);

            hook();
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }

    protected abstract void hook() throws Exception;
}
