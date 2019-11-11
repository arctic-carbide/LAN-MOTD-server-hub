package base;

public class Main {

    public static void main(String[] args) {
        String IP = args[0];

        try {
            checkArgumentLength(args);
            Client client = new Client(IP);

            client.start();

        }
        catch (Exception e) {
            System.err.println(e);
        }

    }

    private static void checkArgumentLength(String[] args) {
        if (args.length < 1) {
            throw new IllegalArgumentException();
        }
    }

}
