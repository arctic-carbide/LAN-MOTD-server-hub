package roles;

import commands.ServerCommand;

public class RootUser extends BasicUser {
    public static final String ROOT_NAME = "root";

    public RootUser() {
        super(ROOT_NAME);
    }

    @Override
    protected boolean hasCommandPermission(ServerCommand command) {
        return command instanceof ServerCommand;
    }
}
