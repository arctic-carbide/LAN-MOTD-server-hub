package roles;

import commands.ServerCommand;
import java.util.HashSet;

public abstract class UserProfile {

    protected static final HashSet<ServerCommand> whitelist = new HashSet<>();
    protected ServerCommand command = ServerCommand.DEFAULT_COMMAND;

    public abstract void call(String cmd);
}
