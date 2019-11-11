package base;

import java.net.InetAddress;

public class Main {

    public static void main(String[] args) {
        InetAddress inetAddress;

        try {
            inetAddress = InetAddress.getLocalHost();
            System.out.println("Address: " + inetAddress.getHostAddress());
            System.out.println("Host Name: " + inetAddress.getHostName());

            ServerManager manager = ServerManager.getInstance();
            manager.start();
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }

}
