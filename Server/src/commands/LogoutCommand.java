package commands;

import base.ServerResponseCode;
import roles.AnonymousUser;
import roles.UserProfile;

public class LogoutCommand extends ServerCommand {

    public void call() {
        logUserOutOfServer();
    }

    private void logUserOutOfServer() {
        // rootUser = false;
        UserProfile user = reference.getUser();
        System.out.println("Logging user out...");

        reference.setUser(new AnonymousUser());
        reference.getOS().println(ServerResponseCode.OK);

        System.out.println("Logout success!");
    }

}
