package roles;

import commands.AnonymousUserCommand;
import commands.ServerCommand;
import exceptions.Server401Exception;
import exceptions.UnauthorizedCommandException;

public class AnonymousUser extends UserProfile {

    @Override
    public void call(String commandLine) throws Server401Exception {
        ServerCommand command = ServerCommand.select(commandLine);

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
