package base;

import shared.ServerMemberData;

// Allocates threads for each client connection to a server instance
public class ServerManager  {

    // SINGLETON BLOCK
    private static ServerManager instance;
    private ServerMemberData serverData;

    private ServerManager() throws Exception {
        serverData = new ServerMemberData();
        ServerInstance.init();
    }

    public static ServerManager getInstance() throws Exception {
         if (instance == null) {
             instance = new ServerManager();
         }

         return instance;
    }
    // END BLOCK

    public void start() {
        ServerInstance currentInstance;
        Thread thread;

        while (true) {
            try {
                serverData.establishConnection();

                currentInstance = new ServerInstance(serverData);
                thread = new Thread(currentInstance);

                thread.start();
            }
            catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
