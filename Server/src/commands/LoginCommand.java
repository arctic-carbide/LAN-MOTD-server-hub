package commands;

import base.ServerResponseCode;
import roles.KnownUser;
import roles.RootUser;

public class LoginCommand extends ServerCommand {
    public void call() {
        logClientIntoServer();
    }

    private void logClientIntoServer() {
        System.out.println("Starting LOGIN procedure...");

        System.out.println("Checking arguments...");
        if (partitionedInput.size() < 2) {
            os.println(ServerResponseCode.FAIL + "Usage: LOGIN <username> <password>");
            return;
        }

        System.out.println("Acquiring arguments...");
        String username = partitionedInput.poll();
        String password = partitionedInput.poll();
        String requiredPassword = users.get(username);

        System.out.println("Comparing supplied password to registered user password...");
        if (password.equals(requiredPassword)) {
            System.out.println("Determining user type...");
            if (username.equals(ROOT_NAME)) {
                user = new RootUser();
            }
            else {
                user = new KnownUser();
            }

            System.out.println("Login success!");
            os.println(ServerResponseCode.OK);
        }
        else {
            System.out.println("Login fail!");
            os.println(ServerResponseCode.FAIL + "UserID or Password");
        }
    }
}
