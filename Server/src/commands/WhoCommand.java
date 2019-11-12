package commands;


import roles.UserProfile;

public class WhoCommand extends BasicUserCommand {

    public void call() throws Exception {
        listAllConnectedUsers();
    }

    private void listAllConnectedUsers() {

        server.getOS().println("Active Users:");
        for (UserProfile u : server.getActiveUsers()) {
            server.getOS().println(u.getFormattedInfo());
        }

    }

}
