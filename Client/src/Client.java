/* 
 * Client.java
 */

import java.io.*;
import java.net.*;

public class Client 
{
    public static final int SERVER_PORT = 7243;
    private static final boolean DEBUG = false;

	// private static BufferedReader stdin = null;
	private static Socket clientSocket = null;
	private static PrintStream os = null;
	private static BufferedReader is = null;
	private static String userInput = null;
	private static String serverInput = null;
	private static BufferedReader stdInput = null;

	private static void Display(String msg) {
		if (DEBUG) {
			System.out.println(msg);
		}
	}

	private static void test(String[] args) {

		//Check the number of command line parameters
		if (args.length < 1)
		{
			System.out.println("Usage: client <Server IP Address>");
			System.exit(1);
		}

		// Try to open a socket on SERVER_PORT
		// Try to open input and output streams
		try
		{
			Display("Initializing Client...");

			clientSocket = new Socket(args[0], SERVER_PORT);
			os = new PrintStream(clientSocket.getOutputStream());
			is = new BufferedReader (
					new InputStreamReader(clientSocket.getInputStream()));
			stdInput = new BufferedReader(new InputStreamReader(System.in));

			Display("Client initialized!");
		}
		catch (UnknownHostException e)
		{
			System.err.println("Don't know about host: hostname");
		}
		catch (IOException e)
		{
			System.err.println("Couldn't get I/O for the connection to: hostname");
		}

		// If everything has been initialized then we want to write some data
		// to the socket we have opened a connection to on port 25

        if (clientSocket != null && os != null && is != null)
		{
            try
			{
				do
				{
					userInput = acquireClientInput();
					os.println(userInput);
					serverInput = is.readLine();

					switch (userInput) {
						case "MSGGET":
							serverInput += "\n" + is.readLine();
							break;
						case "MSGSTORE":
							if (serverInput.equals("200 OK")) {
								System.out.println(serverInput);
								userInput = stdInput.readLine();
								os.println(userInput);
								serverInput = is.readLine();
							}
							break;
					}

					System.out.println(serverInput);

				} while (!userInput.equals("QUIT"));

				// close the input and output stream
				// close the socket

				os.close();
				is.close();
				clientSocket.close();
			}
			catch (IOException e)
			{
				System.err.println("IOException: " + e);
			}
			catch (Exception e) {
				System.err.println("Exception: " + e);
			}
		}
	}

	private static String acquireClientInput() throws Exception {
		Display("Waiting for client input...");
		String response = stdInput.readLine();

		return response;
	}

    public static void main(String[] args) 
    {
    	System.out.println("Client started!");

    	try {
    		test(args);
		}
    	catch (Exception e) {
    		System.err.println(e);
    		System.exit(-1);
		}

    	System.out.println("Client terminated!");
    }
}
