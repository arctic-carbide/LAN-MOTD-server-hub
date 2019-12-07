package commands;

import roles.UserProfile;

public class ShutdownCommand extends ServerCommand {

    public void call() throws Exception {
        shutdownServer();
    }

    private void shutdownServer() {
        for (UserProfile user : server.getActiveUsers()) {
            user.getServer().getOS().println("210 Server is shutting down...");
        }

        System.exit(0); // kills everything
    }

}
