package base;

import java.io.BufferedReader;

public class MessageReceiver implements Runnable {
    private BufferedReader input;

    public MessageReceiver(BufferedReader br) {
        input = br;
    }

    @Override
    public void run() {
        try {
            monitorInputStream();
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }

    private void monitorInputStream() throws Exception {
        String message;

        while (true) {
            message = input.readLine(); // blocks if there is nothing to read
            System.out.println(message);
        }
    }
}
