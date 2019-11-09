package roles;

import commands.ServerCommand;

public class RootUser extends KnownUser {

    public RootUser(String uname) {
        super(uname);
    }

    @Override
    protected boolean hasCommandPermission(ServerCommand command) {
        return command instanceof ServerCommand;
    }
}
