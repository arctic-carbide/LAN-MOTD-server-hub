package commands;

public class ShutdownCommand extends ServerCommand {

    public void call() {
        shutdownServer();
    }

    private void shutdownServer() {
        System.exit(0); // kills everything
    }

}
