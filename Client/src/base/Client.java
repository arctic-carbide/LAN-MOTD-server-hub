package base;/*
 * base.Client.java
 */

import java.io.*;
import java.net.Socket;

import actions.UserAction;
import shared.*;

public class Client extends Node {
	private BufferedReader stdInput;

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
			userCommandLine = getUserInput();
			commandParts = userCommandLine.split(" ");

			exit = isExitCommand(commandParts[0]);
			sendToServer(userCommandLine);

			if (!exit) {
				action = UserAction.determineAction(this, commandParts[0]);
				action.execute();
			}

		} while (!exit);
	}

	public void start() {

		// If everything has been initialized then we want to write some data
		try {
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
