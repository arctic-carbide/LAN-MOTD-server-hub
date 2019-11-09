package base;/*
 * base.Server.java
 */

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import roles.*;
import shared.*;

public class ServerInstance implements Runnable {
    public static final String ROOT_NAME = "root";
    public static final int MESSAGE_LIMIT = 20;
    public static final int SERVER_PORT = 7243;

    private static String messageOfTheDay = null;
    private static CircularQueue<String> messages = new CircularQueue<>(MESSAGE_LIMIT);
    private static BufferedWriter ofstream;
    private static List<String> usersAndPasswords;
    private static HashMap<String, String> users;

    private static ServerSocket myServerice = null;
    private static String line;
    private static BufferedReader is;
    private static PrintStream os;
    private static Socket serviceSocket = null;
    private UserProfile user = new AnonymousUser();

//    private static final boolean DEBUG = false;

    public ServerInstance() {
        try {
            init();
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void run() {

    }

    public void init() throws Exception {
        Utility.display("Initializing internal server data...");

        String msgsFilename = "messages.txt"; // "base.Server/resources/messages.txt";
        String usersFilename = "users.txt"; // "base.Server/resources/users.txt";

        InitUsersFromFile(usersFilename);
        QueueMessagesFromFile(msgsFilename);
        OpenMessagesFile(msgsFilename);
        // ValidateMessages();

        Utility.display("base.Server initialized!");
    }

    public void setMOTD(String motd) { messageOfTheDay = motd;}
    public String getMOTD() { return messageOfTheDay; }
    public PrintStream getOS() { return os; }
    public CircularQueue<String> getMessageQueue() { return messages; }
    public boolean isUserLoggedIn() { return user instanceof KnownUser; }
    public BufferedReader getIS() { return is; }
    public BufferedWriter getOfstream() { return ofstream; }
    public UserProfile getUser() { return user; }
    public void setUser(UserProfile profile) { user = profile; }
    public HashMap<String, String> getUsers() { return users; }

    private static void InitUsersFromFile(String filename) throws Exception {
        Utility.display("Try to get users and passwords from provided file...");

        URL usersURL = ServerInstance.class.getResource(filename);
        users = new HashMap<>();

        usersAndPasswords = Files.readAllLines(Paths.get(usersURL.toURI()), StandardCharsets.UTF_8);
        for (String m : usersAndPasswords) {
            String[] partition = m.split(" ");
            users.put(partition[0], partition[1]);
        }

        Utility.display("Users registered!");
    }

    private static void QueueMessagesFromFile(String filename) throws Exception {
        Utility.display("Try to queue stored messages to internal message queue...");

        URL msgs = ServerInstance.class.getResource(filename);
        List<String> fileMessages = null;

        fileMessages = Files.readAllLines(Paths.get(msgs.toURI()), StandardCharsets.UTF_8);
        for (String m : fileMessages) {
            messages.add(m); // this is a circular queue: elements removed from the front go to the back of the queue
        }

        Utility.display("Messages queued!");
    }

    private static void OpenMessagesFile(String filename) throws Exception {
        Utility.display("Try to open messages file for writing...");

        FileWriter temp = new FileWriter(filename, true);
        ofstream = new BufferedWriter(temp);

        Utility.display("File is ready to be written to!");
    }

    public void start()  {

        openServerSocket();

		// Create a socket object from the ServerSocket to listen and accept connections.
		// Open input and output streams

//		while (true) // always run until an exception occurs
//		{
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
			catch (Exception e) {
			    System.err.println(e.getMessage());
            }
//		}
	}

	private static void endConnection() throws Exception {
        //close input and output stream and socket
        is.close();
        os.close();
        serviceSocket.close();

    }

	private void interpretClientInput() throws Exception {
        // As long as we receive data, echo that data back to the client.

        while ((line = is.readLine()) != null)
        {
            user.call(line);
        }

    }

    private static LinkedList<String> partitionClientInput(String command) {
        String[] a = command.split(" ");
        List<String> l = Arrays.asList(a);

        return new LinkedList<>(l);
    }

	private static void Quit() {
        Utility.display("Starting QUIT procedure...");

        os.println(ServerResponseCode.OK);
        Logout(); // logout the user when they leave

        Utility.display("Client disconnected from server!");
    }

	private static void Shutdown() throws Exception {
        Utility.display("Starting SHUTDOWN procedure...");

        if (isRootUser()) {
            os.println(ServerResponseCode.OK);
            Utility.display("Shutting down server...");
            // System.out.println("Shutting down server...");

            is.close();
            os.close();
            serviceSocket.close();
            System.exit(0);
        }
        else {
            Utility.display("SHUTDOWN procedure aborted!");
            os.println(ServerResponseCode.FAIL + "You are not currently logged in, log in first");
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

        user = ((AnonymousUser) user);
        os.println(ServerResponseCode.OK);

        System.out.println("Logout success!");
    }

	private static void Login() {
        System.out.println("Starting LOGIN procedure...");

        System.out.println("Checking arguments...");
        if (partitionedInput.size() < 2) {
            os.println(ServerResponseCode.FAIL + "Usage: LOGIN <username> <password>");
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
                user = new RootUser();
            }
            else {
                user = new KnownUser();
            }

            System.out.println("Login success!");
            os.println(ServerResponseCode.OK);
        }
        else {
            System.out.println("Login fail!");
            os.println(ServerResponseCode.FAIL + "UserID or Password");
        }
    }

//    private static boolean UserIsLoggedIn() {
//        Utility.display("Checking if client is logged in...");
//        return user != null;
//    }

    public static boolean isRootUser() {
        Utility.display("Checking if client user is root user...");
        return user instanceof RootUser;
    }

//	private static void StoreMessage() throws Exception {
//        String message = null;
//
//        Utility.display("Starting MSGSTORE procedure...");
//        // System.out.println("Starting MSGSTORE procedure...");
//
//        // if (!rootUser) {
//        if (!UserIsLoggedIn()) { // nobody is logged in
//            Utility.display("Procedure MSGSTORE aborted!");
//            os.println(ServerResponseCode.FAIL + "You are not currently logged in, log in first");
//            return;
//        }
//        else {
//            os.println(ServerResponseCode.OK);
//        }
//
//        Utility.display("Waiting for user message...");
//        message = is.readLine();
//
//        // try to add the message to the server
//        // the queue will check to see if it can fit the message in its internal structure
//        // if it can, it will add it and return true
//        // else, if doesn't and returns false
//
//        Utility.display("Try to store user message into server...");
//        // System.out.println("Try to store user message into server...");
//        if (messages.offer(message)) {
//            ofstream.write(message); // append that message to the external file
//            ofstream.newLine();
//            // ofstream.flush();
//            Utility.display("Procedure MSGSTORE complete!");
//            os.println(ServerResponseCode.OK); // confirm that the message was added
//            // System.out.println("Procedure success!");
//        }
//        else {
//            os.println(ServerResponseCode.FAIL + "Too Many Messages in Memory: " + MESSAGE_LIMIT); // confirm that adding the message failed
//            Utility.display("Procedure MSGSTORE failed!");
//            // System.out.println("Procedure failed!");
//        }
//    }
}
