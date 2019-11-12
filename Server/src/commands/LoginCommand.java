package commands;

import shared.ServerResponseCode;
import exceptions.Server401Exception;
import exceptions.InsufficientLoginArguementsException;
import exceptions.InvalidLoginArgumentsException;
import roles.*;

public class LoginCommand extends AnonymousUserCommand {
    private String username;
    private String password;

    public LoginCommand(String... args) {
        validateCommandArguments(args);
        username = args[0];
        password = args[1];
    }


    public void call() throws Exception {
        try {
            logClientIntoServer();
            server.getOS().println(ServerResponseCode.OK.VALUE);
        }
        catch (Server401Exception e) {
            server.getOS().println(e.getMessage());
        }
    }

    private void validateCommandArguments(String[] args) throws Server401Exception {

        System.out.println("Checking arguments...");
        if (args.length < 2) {
            throw new InsufficientLoginArguementsException();
        }
    }

    private void validateUser() throws Server401Exception {
        if (password == null) {
            throw new InvalidLoginArgumentsException();
        }
    }

    private void determineUserType() {
        System.out.println("Determining user type...");

        server.getActiveUsers().remove(server.getUser());
        if (username.equals(RootUser.ROOT_NAME)) {
            server.transformUser(new RootUser(server));
        }
        else {
            server.transformUser(new BasicUser(server, username));
        }
        server.getActiveUsers().add(server.getUser());
    }

    private void validatePassword() {
        System.out.println("Acquiring arguments...");
        String requiredPassword = server.getUsers().get(username); // retrieves the password value associated with user

        System.out.println("Comparing supplied password to registered user password...");
        if (password.equals(requiredPassword)) {
            determineUserType();
        }
        else {
            System.out.println("Login fail!");
            throw new InvalidLoginArgumentsException();
            // server.getOS().println(ServerResponseCode.FAIL + "UserID or Password");
        }
    }

    private void logClientIntoServer() throws Server401Exception {
        validateUser();
        validatePassword();
        determineUserType();
    }
}
