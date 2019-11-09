package roles;

import commands.KnownUserCommand;
import commands.ServerCommand;

public class KnownUser extends AnonymousUser {
    private String username;

    public KnownUser(String username) {
        this.username = username;
    }

    public String getUsername() { return username; }

    @Override
    protected boolean hasCommandPermission(ServerCommand command) {
        return command instanceof KnownUserCommand;
    }
}
