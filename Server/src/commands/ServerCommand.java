package commands;

import base.NewServer;

public abstract class ServerCommand {
    public static final ServerCommand DEFAULT_COMMAND = new LogoutCommand();
    protected NewServer reference;

    public abstract void call();


    public void setReference(NewServer ref) { reference = ref; }

    public ServerCommand select(String command) {
        switch (command) { // first element is the command
            case "MSGGET":
                return new MessageGetCommand();
            case "MSGSTORE":
                return new MessageStoreCommand();
            case "QUIT":
                return new QuitCommand();
            case "LOGIN":
                return new LoginCommand();
            case "LOGOUT":
                return new LogoutCommand();
            case "SHUTDOWN":
                return new ShutdownCommand();
            case "WHO":
                return new WhoCommand();
            case "SEND":
                return new SendCommand();
            default:
                return new Error401Report("Command not recognized! Please try again.");
        }
    }
}
