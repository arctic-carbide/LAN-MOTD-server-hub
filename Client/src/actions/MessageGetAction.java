package actions;

import base.Client;

public class MessageGetAction extends UserAction {

    public MessageGetAction(Client caller) {
        super(caller, null);
    }

    @Override
    public void hook() throws Exception {
        displayMessage();
    }

    private void displayMessage() throws Exception {
        serverResponse = caller.retrieveServerResponse();
        System.out.println(serverResponse);
    }
}
