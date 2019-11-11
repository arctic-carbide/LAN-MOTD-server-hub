package roles;

import base.ServerInstance;

public abstract class UserProfile {
    protected ServerInstance server;

    protected UserProfile(ServerInstance s) {
        server = s;
    }

    public abstract void call(String cmd);
}
