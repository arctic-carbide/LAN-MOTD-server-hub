package actions;

import base.Client;

public class QuitAction extends UserAction {

    public QuitAction(Client caller) {
        super(caller, null);
    }

    @Override
    public void hook() throws Exception {
        caller.endClientSession();
    }


}
