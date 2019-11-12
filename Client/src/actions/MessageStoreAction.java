package actions;

import base.Client;
import shared.ServerResponseCode;

public class MessageStoreAction extends UserAction {

    MessageStoreAction(Client caller) {
        this.caller = caller;
    }

    @Override
    public void hook() throws Exception {
        sendMOTDMessageToStore();
    }

    private void sendMOTDMessageToStore() throws Exception {
        String message = null;

        if (serverResponse.equals(ServerResponseCode.OK.VALUE)) {
            message = caller.getUserInput();
            caller.sendToServer(message);

//            serverResponse = caller.retrieveServerResponse();
//            System.out.println(serverResponse);
        }
    }
}
