package actions;

import base.Client;

public class SendAction extends UserAction {
    public SendAction(Client c, String r) {
        super(c, r);
    }

    @Override
    protected void hook() throws Exception {
        String message = null;

        message = caller.getUserInput();
        caller.sendToServer(message);
    }
}
