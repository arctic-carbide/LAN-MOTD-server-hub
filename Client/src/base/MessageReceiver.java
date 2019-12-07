package base;

import java.io.BufferedReader;

public class MessageReceiver implements Runnable {
    private BufferedReader input;
    private boolean OK = false;

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

    public boolean serverOK() { return OK; }

    private void monitorInputStream() throws Exception {
        String message;

        while (true) {
            message = input.readLine(); // blocks if there is nothing to read
            System.out.println(message);

            if (message.equals("200 OK")) {
                OK = true;
            }
            else {
                OK = false;
            }

        }
    }
}
