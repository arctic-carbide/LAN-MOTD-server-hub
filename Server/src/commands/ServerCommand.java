package commands;

import base.ServerInstance;
import exceptions.InvalidCommandException;
import exceptions.Server401Exception;
import shared.CommandName;

public abstract class ServerCommand {
    // public static final ServerCommand DEFAULT_COMMAND = new LogoutCommand();
    protected ServerInstance server;

    public void execute(ServerInstance instance) {
        server = instance;
        call();
    }

    public abstract void call();
    public void setServer(ServerInstance ref) { server = ref; }



    public static ServerCommand select(String commandLine) throws Server401Exception {
        String[] parts = commandLine.split(" ");
        CommandName command = CommandName.valueOf(parts[0]);

        switch (command) { // first element is the command
            case MSGGET: return new MessageGetCommand();
            case MSGSTORE: return new MessageStoreCommand();
            case QUIT: return new QuitCommand();
            case LOGIN: return new LoginCommand(parts[1], parts[2]);
            case LOGOUT: return new LogoutCommand();
            case SHUTDOWN: return new ShutdownCommand();
            case WHO: return new WhoCommand();
            case SEND: return new SendCommand();
            default: throw new InvalidCommandException();
        }
    }
}
