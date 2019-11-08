package roles;

import commands.ServerCommand;

public abstract class UserProfile {

    protected ServerCommand command = ServerCommand.DEFAULT_COMMAND;
    public abstract void call(String cmd);
}
