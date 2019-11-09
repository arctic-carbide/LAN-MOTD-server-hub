package base;

public class Main {

    public static void main(String[] args) {
        try {
            ServerManager manager = ServerManager.getInstance();
            manager.start();
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}
