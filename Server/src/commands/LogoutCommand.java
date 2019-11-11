package commands;

import shared.ServerResponseCode;
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

        server.setUser(new AnonymousUser(server));
        server.getOS().println(ServerResponseCode.OK.VALUE);

        System.out.println("Logout success!");
    }

}
