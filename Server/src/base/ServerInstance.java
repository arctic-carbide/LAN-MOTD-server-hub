package base;/*
 * base.Server.java
 */

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import exceptions.Server401Exception;
import roles.*;
import shared.*;
import sun.tools.jar.Main;

public class ServerInstance extends Node implements Runnable {
    public static final int MESSAGE_LIMIT = 20;
    private static CircularQueue<String> messages = new CircularQueue<>(MESSAGE_LIMIT);

    private static String messageOfTheDay = null;
    private static BufferedWriter ofstream;
    private static List<String> usersAndPasswords;
    private static HashMap<String, String> users;
    private static HashSet<UserProfile> activeUsers = new HashSet<>();

    private UserProfile user = new AnonymousUser(this);
    private volatile boolean terminate = false;

    public ServerInstance(ServerManager creator) {
        super(creator); // copies the socket and stream data
    }



    public static void init() throws Exception {
        Utility.display("Initializing internal server data...");

        String msgsFilename = "messages.txt"; // "base.Server/resources/messages.txt";
        String usersFilename = "users.txt"; // "base.Server/resources/users.txt";

        InitUsersFromFile(usersFilename);
        QueueMessagesFromFile(msgsFilename);
        openMessagesFile(msgsFilename);

        Utility.display("base.Server initialized!");
    }

    public void setMOTD(String motd) { messageOfTheDay = motd;}

    public String getMOTD() { return messageOfTheDay; }
    public PrintStream getOS() { return socketOStream; }

    public CircularQueue<String> getMessageQueue() { return messages; }
    public boolean isUserLoggedIn() { return user instanceof BasicUser; }
    public BufferedReader getIS() { return socketIStream; }
    public BufferedWriter getOutputFileStream() { return ofstream; }
    public UserProfile getUser() { return user; }
    public HashMap<String, String> getUsers() { return users; }
    public void terminate() { terminate = true; }
    public HashSet<UserProfile> getActiveUsers() { return activeUsers; }
    public void respondOK(String extra) { socketOStream.println(ServerResponseCode.OK.VALUE + " " + extra);}
    public void respondOK() { socketOStream.println(ServerResponseCode.OK.VALUE);}

    public void transformUser(UserProfile profile) { user = profile; }


    private static void InitUsersFromFile(String filename) throws Exception {
        Utility.display("Try to get users and passwords from provided file...");

        URL usersURL = ServerInstance.class.getClassLoader().getResource(filename);
        if (usersURL == null) throw new FileNotFoundException(filename);

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

        URL msgs = ServerInstance.class.getClassLoader().getResource(filename);
        if (msgs == null) throw new FileNotFoundException(filename);

        List<String> fileMessages;
        fileMessages = Files.readAllLines(Paths.get(msgs.toURI()), StandardCharsets.UTF_8);
        // this is a circular queue: elements removed from the front go to the back of the queue
        messages.addAll(fileMessages);

        Utility.display("Messages queued!");
    }

    private static void openMessagesFile(String filename) throws Exception {
        Utility.display("Try to open messages file for writing...");

        FileWriter temp = new FileWriter(filename, true);
        ofstream = new BufferedWriter(temp);

        Utility.display("File is ready to be written to!");
    }

	private void endConnection() throws Exception {
        //close input and output stream and socket
        activeUsers.remove(user);
        socketIStream.close();
        socketOStream.close();
        socket.close();
    }

	private void interpretClientInput() throws Exception {
        // As long as we receive data, echo that data back to the client.
        String userCommand;
        while (!terminate)
        {
            try {
                userCommand = socketIStream.readLine();
                user.call(userCommand);
                // socketOStream.println(ServerResponseCode.OK.VALUE);
            }
            catch (Server401Exception e) {
                socketOStream.println(e.getMessage());
            }
        }

    }

    @Override
    public void run() {
        try {
            interpretClientInput(); // loops until client disconnects
            endConnection();
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }
}
