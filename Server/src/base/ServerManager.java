package base;

import java.util.LinkedList;

// Allocates threads for each client connection to a server instance
public class ServerManager  {
    private static ServerManager instance;

    private ServerManager() {}

    public static ServerManager getInstance() {
         if (instance == null) {
             instance = new ServerManager();
         }

         return instance;
    }

    public void start() {
        while (true) {

        }
    }
}
