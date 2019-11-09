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

public class ServerInstance implements Runnable {
    public static final int MESSAGE_LIMIT = 20;
    private static CircularQueue<String> messages = new CircularQueue<>(MESSAGE_LIMIT);

    private static String messageOfTheDay = null;
    private static BufferedWriter ofstream;
    private static List<String> usersAndPasswords;
    private static HashMap<String, String> users;

    private ServerMemberData smd;
//    private static ServerSocket myServerice = null;
//    private static Socket serviceSocket = null;
//    private static BufferedReader is;
//    private static PrintStream os;

    private UserProfile user = new AnonymousUser();
    private volatile boolean terminate = false;

    public ServerInstance(ServerMemberData smd) {
        this.smd = new ServerMemberData(smd);
    }


    public ServerInstance() {
        // to avoid deadlock, init outside of class with the manager

//        try {
//
//            init();
//        }
//        catch (Exception e) {
//            System.err.println(e.getMessage());
//        }
    }

    @Override
    public void run() {
        try {
//            // Create a socket object from the ServerSocket to listen and accept connections.
//            openServerSocket();
//
//            // Open input and output streams
//            acquireClientStreams();
            interpretClientInput(); // loops until client disconnects
            endConnection();
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
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

    public PrintStream getOS() { return smd.ostream; }
    public CircularQueue<String> getMessageQueue() { return messages; }
    public boolean isUserLoggedIn() { return user instanceof BasicUser; }
    public BufferedReader getIS() { return smd.istream; }
    public BufferedWriter getOutputFileStream() { return ofstream; }
    public UserProfile getUser() { return user; }
    public void setUser(UserProfile profile) { user = profile; }
    public HashMap<String, String> getUsers() { return users; }
    public void terminate() { terminate = true; }

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
        smd.istream.close();
        smd.ostream.close();
        smd.socket.close();
    }

	private void interpretClientInput() throws Exception {
        // As long as we receive data, echo that data back to the client.
        String userCommand;
        while (!terminate)
        {
            try {
                userCommand = smd.istream.readLine();
                user.call(userCommand);
                smd.ostream.println(ServerResponseCode.OK);
            }
            catch (Server401Exception e) {
                smd.ostream.println(e.getMessage());
            }
        }

    }

//    private static void openServerSocket() throws Exception {
//        myServerice = new ServerSocket(CommunicationData.SERVER_PORT); // establish a socket to put on the target port
//    }
//
//    private static void acquireClientStreams() throws Exception {
//        serviceSocket = myServerice.accept(); //
//        is = new BufferedReader (new InputStreamReader(serviceSocket.getInputStream()));
//        os = new PrintStream(serviceSocket.getOutputStream());
//    }
}
