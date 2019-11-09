package roles;

import base.Server;
import commands.AnonymousUserCommand;
import commands.MessageGetCommand;
import commands.ServerCommand;
import exceptions.Server401Exception;
import exceptions.UnauthorizedCommandException;

import java.util.HashSet;

public class AnonymousUser extends UserProfile {

    public void call(String commandLine) throws Server401Exception {
        ServerCommand command = command.select(commandLine);

        validateCommand(command);
        command.call();
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
