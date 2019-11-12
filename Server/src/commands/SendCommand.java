package commands;

import roles.UserProfile;
import shared.ServerResponseCode;

import java.util.List;

public class SendCommand extends BasicUserCommand {
    private String targetUser;

    public SendCommand(String username) {
        targetUser = username;
    }

    public void call() throws Exception {
        sendMessage();
    }

    private void sendMessage() throws Exception {
        String message;

        for (UserProfile u : server.getActiveUsers()) {

            if (u.getUsername().equals(targetUser)) {

                synchronized (u) { // allow only one thread to send a message to a certain user a time
                    message = server.getIS().readLine();

                    u.getServer().getOS().println(ServerResponseCode.OK.VALUE + " you have a new message from " + u.getUsername());
                    u.getServer().getOS().println(u.getUsername() + ": " + message);
                }

                server.getOS().println(ServerResponseCode.OK.VALUE);
            }

        }

    }

}
