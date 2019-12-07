package base;/*
 * base.Client.java
 */

import java.io.*;
import java.net.Socket;

import actions.NoAction;
import actions.UserAction;
import shared.*;

public class Client extends Node {
	private BufferedReader stdInput;
	// private BufferedReader messages;

	// TODO: COMMUNICATE FROM ONE CLIENT TO ANOTHER USING A SEPARATE SOCKET CHANNEL
	// THE CURRENT CHANNEL IS BEING USED FOR STRICTLY COMMAND AND RESPONSE SIGNALS
	// BUT IF A CLIENT SENDS A MESSAGE TO ANOTHER USER, IT NEEDS TO BE READ IMMEDIATELY
	// THIS IS POSSIBLE OVER A SINGLE SOCKET BUT THE CHANNEL COULD GET BOGGED WITH MESSAGES
	// THE SOLUTION IS TO USE A SEPARATE SOCKET CHANNEL TO COMMUNICATE

	// ON THE CLIENT, CREATE A THREAD TO LISTEN FOR MESSAGES DIRECTED TO IT
	// READ THOSE MESSAGES AND DISPLAY THEM IMMEDIATELY
	// IN THIS CASE, WE WILL ASSUME IMMEDIATELY MEANS

	// ON A SINGLE CHANNEL, WHAT WE CAN DO IS CHECK THE MAIN SOCKET CHANNEL AFTER EVERY COMMAND SENT
	// THIS MEANS THE SERVER WILL HAVE TO WITHHOLD THE MESSAGE UNTIL IT'S DONE HANDLING THE LAST COMMAND

	public Client(String IP) throws Exception {
		establishConnection(IP);
		init();
	}

	public void establishConnection(String IP) throws Exception {
		socket = new Socket(IP, SOCKET_PORT);
		acquireSocketStreams();
	}

	public boolean isConnected() {
		boolean a = socket != null;
		boolean b = socketIStream != null;
		boolean c = socketOStream != null;

		return a && b && c;
	}

	private void init() {
		stdInput = new BufferedReader(new InputStreamReader(System.in));
	}

	public String retrieveServerResponse() throws Exception {
		return socketIStream.readLine();
	}

	public void sendToServer(String command) {
		socketOStream.println(command);
	}

	private boolean isExitCommand(String command) {
		CommandName exit = CommandName.QUIT;
		CommandName name = CommandName.valueOf(command);

		if (name.equals(exit)) {
			return true;
		}
		else {
			return false;
		}
	}

	private void communicateOverSocket() throws Exception {
		boolean exit = false;
		String userCommandLine = null;
		String[] commandParts;
		UserAction action = null;

		do {
			System.out.print(">");
			userCommandLine = getUserInput();
			commandParts = userCommandLine.split(" ");

//			exit = isExitCommand(commandParts[0]);
			try {
				action = UserAction.determineAction(this, commandParts);
				sendToServer(userCommandLine);

				if (!action.isQuit()) {
					action.execute();
				}
			}
			catch (Exception e) {
				System.out.println("Invalid Command");
				action = new NoAction();
			}

			// socketIStream.ready(); // wait for the server to come back with a response
		} while (!action.isQuit());
	}

	public MessageReceiver messages;
	private void instantiateMessageMonitor() {
		messages = new MessageReceiver(socketIStream);
		Thread thread = new Thread(messages);

		thread.start();
	}

	public void start() {

		try {
			instantiateMessageMonitor();
			communicateOverSocket();
		}
		catch (Exception e) {
			System.err.println(e);
		}
	}

	public void endClientSession() throws Exception {
		socketIStream.close();
		socketOStream.close();
		socket.close();
	}

	public String getUserInput() throws Exception {
		// System.out.print("Command: ");
		return stdInput.readLine();
	}
}
