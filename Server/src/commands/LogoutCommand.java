package commands;

import base.ServerResponseCode;
import roles.AnonymousUser;
import roles.UserProfile;

public class LogoutCommand extends BasicUserCommand {

    public void call() {
        logUserOutOfServer();
    }

    private void logUserOutOfServer() {
        // rootUser = false;
        UserProfile user = server.getUser();
        System.out.println("Logging user out...");

        server.setUser(new AnonymousUser());
        server.getOS().println(ServerResponseCode.OK);

        System.out.println("Logout success!");
    }

}
