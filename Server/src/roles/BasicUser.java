package roles;

import base.ServerInstance;
import commands.BasicUserCommand;
import commands.ServerCommand;

public class BasicUser extends AnonymousUser {
    private String username;

    public BasicUser(ServerInstance instance, String username) {
        super(instance);
        this.username = username;
    }

    public String getUsername() { return username; }

    @Override
    protected boolean hasCommandPermission(ServerCommand command) {
        return command instanceof BasicUserCommand;
    }
}
