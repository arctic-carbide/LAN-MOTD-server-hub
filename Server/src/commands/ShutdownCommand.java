package commands;

public class ShutdownCommand extends ServerCommand {

    public void call() throws Exception {
        shutdownServer();
    }

    private void shutdownServer() {
        System.exit(0); // kills everything
    }

}
