package commands;

import base.ServerInstance;
import exceptions.InvalidCommandException;
import exceptions.Server401Exception;

public abstract class ServerCommand {
    public static final ServerCommand DEFAULT_COMMAND = new LogoutCommand();
    protected ServerInstance server;

    public abstract void call();
    public void setServer(ServerInstance ref) { server = ref; }

    public static ServerCommand select(String command) throws Server401Exception {
        String[] parts = command.split(" ");

        switch (parts[0]) { // first element is the command
            case "MSGGET":
                return new MessageGetCommand();
            case "MSGSTORE":
                return new MessageStoreCommand();
            case "QUIT":
                return new QuitCommand();
            case "LOGIN":
                return new LoginCommand(parts[1], parts[2]);
            case "LOGOUT":
                return new LogoutCommand();
            case "SHUTDOWN":
                return new ShutdownCommand();
            case "WHO":
                return new WhoCommand();
            case "SEND":
                return new SendCommand();
            default:
                throw new InvalidCommandException();
        }
    }
}
