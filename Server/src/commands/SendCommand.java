package commands;

import exceptions.NullUserException;
import roles.UserProfile;
import shared.ServerResponseCode;

public class SendCommand extends BasicUserCommand {
    private String targetUser;

    public SendCommand(String username) {
        targetUser = username;
    }

    public void call() throws Exception {
        UserProfile user = findUser();
        sendMessageToUser(user);
    }

    private UserProfile findUser() throws Exception {
        UserProfile userFound = null;

        for (UserProfile u : server.getActiveUsers()) {
            if (u.getUsername().equals(targetUser)) {
                userFound = u;
                break;
            }
        }

        if (userFound == null) {
            throw new NullUserException();
        }

        server.respondOK();
        return userFound;
    }

    private void sendMessageToUser(UserProfile recipient) throws Exception {
        String message = null;
        String senderName = null;

        synchronized (server.getIS()) {
            message = server.getIS().readLine();
            senderName = server.getUser().getUsername();

            recipient.getServer().respondOK("you have a new message from " + senderName);
            recipient.getServer().getOS().println(senderName + ": " + message);
        }

        // server.respondOK();
    }

}
