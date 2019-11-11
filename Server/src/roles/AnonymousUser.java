package roles;

import base.ServerInstance;
import commands.AnonymousUserCommand;
import commands.ServerCommand;
import exceptions.Server401Exception;
import exceptions.UnauthorizedCommandException;

import java.awt.event.WindowStateListener;

public class AnonymousUser extends UserProfile {

    public AnonymousUser(ServerInstance instance) {
        super(instance);
    }

    @Override
    public void call(String commandLine) throws Server401Exception {
        ServerCommand command = ServerCommand.select(commandLine);

        validateCommand(command);
        command.execute(server);
    }

    protected void validateCommand(ServerCommand command) {
        if (!hasCommandPermission(command)) {
            throw new UnauthorizedCommandException();
        }
    }

    protected boolean hasCommandPermission(ServerCommand command) {
        return command instanceof AnonymousUserCommand;
    }

}
