package roles;

import base.ServerInstance;
import java.net.InetAddress;

public abstract class UserProfile {
    protected ServerInstance server;
    protected InetAddress ipAddress;
    protected String username = "n/a";

    protected UserProfile(ServerInstance instance) {
        server = instance;
        ipAddress = instance.getSocket().getInetAddress();
    }

    public String getFormattedInfo() {
        return getUsername() + "\t" + getIPAddress();
    }

    public ServerInstance getServer() { return server; }
    public String getUsername() { return username; }
    public String getIPAddress() { return ipAddress.getHostAddress(); }

    public abstract void call(String cmd);
}
