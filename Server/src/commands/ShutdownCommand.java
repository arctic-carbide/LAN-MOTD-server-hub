package commands;

public class ShutdownCommand extends ServerCommand {

    public void call() {
        shutdownServer();
    }

    private void shutdownServer() {
        // TODO: FINISH SHUTDOWN COMMAND
        // kill all running threads
        // end server manager
    }

}
