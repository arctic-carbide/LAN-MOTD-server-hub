package commands;

import base.ServerInstance;
import com.sun.security.ntlm.Server;
import exceptions.InvalidCommandException;
import exceptions.Server401Exception;
import shared.CommandName;
import shared.ServerResponseCode;

public abstract class ServerCommand {
    // public static final ServerCommand DEFAULT_COMMAND = new LogoutCommand();
    protected ServerInstance server;

    public void execute(ServerInstance instance) {
        try {
            server = instance;

            call(); // defined in derived classes

            server.getOS().println(ServerResponseCode.OK.VALUE); // printed if no exceptions occurred
        }
        catch (Server401Exception e) { // error displayed to user

            server.getOS().println(e.getMessage());

        }
        catch (Exception e) {

        }
    }

    protected abstract void call() throws Exception;
    // public void setServer(ServerInstance ref) { server = ref; }



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
            case SEND: return new SendCommand(parts[1]);
            default: throw new InvalidCommandException();
        }
    }
}
