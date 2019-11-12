package commands;

public class QuitCommand extends AnonymousUserCommand {

    public void call() throws Exception {
        // TODO: FINISH QUIT COMMAND
        terminateClientConnection();
    }

    private void terminateClientConnection() {
        server.terminate(); // puts the thread in a state to cleanup and end running
    }
}
