package base;/*
 * base.Server.java
 */

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

public class Server {
    private static final String OK_MESSAGE = "200 OK";
    private static final String FAIL_CODE = "401 ";
    private static final String ROOT_NAME = "root";
    private static final int MESSAGE_LIMIT = 20;
    public static final int SERVER_PORT = 7243;

    private static String messageOfTheDay = null;
    private static boolean test = false;
    private static CircularQueue<String> messages = new CircularQueue<>(MESSAGE_LIMIT);
    private static BufferedWriter ofstream;
    private static boolean rootUser = false;
    private static List<String> usersAndPasswords;
    private static LinkedList<String> partitionedInput;
    private static HashMap<String, String> users;

    private static ServerSocket myServerice = null;
    private static String line;
    private static BufferedReader is;
    private static PrintStream os;
    private static Socket serviceSocket = null;
    private static User user = null;

    private static final boolean DEBUG = false;

    private static void Display(String msg) {
        if (DEBUG) {
            System.out.println(msg);
        }
    }

    public static void init() throws Exception {
        Display("Initializing internal server data...");

        String msgsFilename = "messages.txt"; // "base.Server/resources/messages.txt";
        String usersFilename = "users.txt"; // "base.Server/resources/users.txt";

        InitUsersFromFile(usersFilename);
        QueueMessagesFromFile(msgsFilename);
        OpenMessagesFile(msgsFilename);
        ValidateMessages();

        Display("base.Server initialized!");
    }

    private static void InitUsersFromFile(String filename) throws Exception {
        Display("Try to get users and passwords from provided file...");

        URL usersURL = Server.class.getResource(filename);
        users = new HashMap<>();

        usersAndPasswords = Files.readAllLines(Paths.get(usersURL.toURI()), StandardCharsets.UTF_8);
        for (String m : usersAndPasswords) {
            String[] partition = m.split(" ");
            users.put(partition[0], partition[1]);
        }

        Display("Users registered!");
    }

    private static void QueueMessagesFromFile(String filename) throws Exception {
        Display("Try to queue stored messages to internal message queue...");

        URL msgs = Server.class.getResource(filename);
        List<String> fileMessages = null;

        fileMessages = Files.readAllLines(Paths.get(msgs.toURI()), StandardCharsets.UTF_8);
        for (String m : fileMessages) {
            messages.add(m); // this is a circular queue: elements removed from the front go to the back of the queue
        }

        Display("Messages queued!");
    }

    private static void OpenMessagesFile(String filename) throws Exception {
        Display("Try to open messages file for writing...");

        FileWriter temp = new FileWriter(filename, true);
        ofstream = new BufferedWriter(temp);

        Display("File is ready to be written to!");
    }

    private static void ValidateMessages() {
        Display("Confirming messages are queued...");

        if (DEBUG) {
            // validate to make sure the messages appear in the structure
            for (String m : messages) {
                if (m != null) {
                    System.out.println(m);
                }
            }
        }

        Display("Validation complete!");
    }

    private static void run() throws Exception
	{

		openServerSocket();

		// Create a socket object from the ServerSocket to listen and accept connections.
		// Open input and output streams

		while (true) // always run until an exception occurs
		{
			try 
			{
			    acquireClientStreams();
                interpretClientInput(); // loops until client disconnects
                endConnection();
			}
			catch (IOException e)
			{
				System.out.println(e);
			}
		}
	}

	private static void endConnection() throws Exception {
        //close input and output stream and socket
        is.close();
        os.close();
        serviceSocket.close();

    }

	private static void interpretClientInput() throws Exception {
        // As long as we receive data, echo that data back to the client.
        String command;

        while ((line = is.readLine()) != null)
        {
            partitionedInput = partitionClientInput(line);
            command = partitionedInput.poll();

            performClientCommand(command);
        }

    }

    private static LinkedList<String> partitionClientInput(String command) {
        String[] a = command.split(" ");
        List<String> l = Arrays.asList(a);

        return new LinkedList<>(l);
    }

    private static void performClientCommand(String command) throws Exception {

        switch (command) { // first element is the command
            case "MSGGET":
                GetMessage();
                break;
            case "MSGSTORE":
                StoreMessage();
                break;
            case "QUIT":
                Quit();
                break;
            case "LOGIN":
                Login();
                break;
            case "LOGOUT":
                Logout();
                break;
            case "SHUTDOWN":
                Shutdown();
                break;
            default:
                os.println(FAIL_CODE + "Command not recognized! Please try again.");
                break;
        }

    }

	private static void Quit() {
        Display("Starting QUIT procedure...");

        os.println(OK_MESSAGE);
        Logout(); // logout the user when they leave

        Display("Client disconnected from server!");
    }

	private static void Shutdown() throws Exception {
        Display("Starting SHUTDOWN procedure...");

        if (UserIsRoot()) {
            os.println(OK_MESSAGE);
            Display("Shutting down server...");
            // System.out.println("Shutting down server...");

            is.close();
            os.close();
            serviceSocket.close();
            System.exit(0);
        }
        else {
            Display("SHUTDOWN procedure aborted!");
            os.println(FAIL_CODE + "You are not currently logged in, log in first");
        }
    }

    private static void openServerSocket() {
        // Try to open a server socket
        try {
            myServerice = new ServerSocket(SERVER_PORT); // establish a socket to put on the target port
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void acquireClientStreams() throws Exception {
        serviceSocket = myServerice.accept(); //
        is = new BufferedReader (new InputStreamReader(serviceSocket.getInputStream()));
        os = new PrintStream(serviceSocket.getOutputStream());
    }

    private static void Logout() {
        // rootUser = false;
        System.out.println("Logging user out...");

        user = null;
        os.println(OK_MESSAGE);

        System.out.println("Logout success!");
    }

	private static void Login() {
        System.out.println("Starting LOGIN procedure...");

        System.out.println("Checking arguments...");
        if (partitionedInput.size() < 2) {
            os.println(FAIL_CODE + "Usage: LOGIN <username> <password>");
            return;
        }

        System.out.println("Acquiring arguments...");
        String username = partitionedInput.poll();
        String password = partitionedInput.poll();
        String requiredPassword = users.get(username);

        System.out.println("Comparing supplied password to registered user password...");
        if (password.equals(requiredPassword)) {
            System.out.println("Determining user type...");
            if (username.equals(ROOT_NAME)) {
                user = new Root();
            }
            else {
                user = new User();
            }

            System.out.println("Login success!");
            os.println(OK_MESSAGE);
        }
        else {
            System.out.println("Login fail!");
            os.println(FAIL_CODE + "UserID or Password");
        }
    }

    private static boolean UserIsLoggedIn() {
        Display("Checking if client is logged in...");
        return user != null;
    }

    private static boolean UserIsRoot() {
        Display("Checking if client user is root user...");
        return user instanceof Root;
    }

	private static void StoreMessage() throws Exception {
        String message = null;

        Display("Starting MSGSTORE procedure...");
        // System.out.println("Starting MSGSTORE procedure...");

        // if (!rootUser) {
        if (!UserIsLoggedIn()) { // nobody is logged in
            Display("Procedure MSGSTORE aborted!");
            os.println(FAIL_CODE + "You are not currently logged in, log in first");
            return;
        }
        else {
            os.println(OK_MESSAGE);
        }

        Display("Waiting for user message...");
        message = is.readLine();

        // try to add the message to the server
        // the queue will check to see if it can fit the message in its internal structure
        // if it can, it will add it and return true
        // else, if doesn't and returns false

        Display("Try to store user message into server...");
        // System.out.println("Try to store user message into server...");
        if (messages.offer(message)) {
            ofstream.write(message); // append that message to the external file
            ofstream.newLine();
            // ofstream.flush();
            Display("Procedure MSGSTORE complete!");
            os.println(OK_MESSAGE); // confirm that the message was added
            // System.out.println("Procedure success!");
        }
        else {
            os.println(FAIL_CODE + "Too Many Messages in Memory: " + MESSAGE_LIMIT); // confirm that adding the message failed
            Display("Procedure MSGSTORE failed!");
            // System.out.println("Procedure failed!");
        }
    }

    private static void GetMessage() {
        Display("Starting MSGGET procedure...");

        os.println(OK_MESSAGE);
        messageOfTheDay = messages.next(); // cycle through to the next message
        os.println(messageOfTheDay);

        Display("Procedure success!");
    }

	public static void main(String[] args)  {
        System.out.println("Starting server...");

        try {
            Server.init();
            Server.run();
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }

        System.out.println("base.Server terminating...");
	}
}
