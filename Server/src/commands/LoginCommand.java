package commands;

import base.ServerResponseCode;
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


    public void call() {
        try {
            logClientIntoServer();
            server.getOS().println(ServerResponseCode.OK);
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

    private void validateUser(String password) throws Server401Exception {
        if (password == null) {
            throw new InvalidLoginArgumentsException();
        }
    }

    private void determineUserType() {

    }

    private void logClientIntoServer() throws Server401Exception {
        System.out.println("Starting LOGIN procedure...");

        System.out.println("Acquiring arguments...");
        String requiredPassword = server.getUsers().get(username); // retrieves the password value associated with user

        System.out.println("Comparing supplied password to registered user password...");
        if (password.equals(requiredPassword)) {
            System.out.println("Determining user type...");
            if (username.equals(ROOT_NAME)) {
                user = new RootUser();
            }
            else {
                user = new KnownUser(username);
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
