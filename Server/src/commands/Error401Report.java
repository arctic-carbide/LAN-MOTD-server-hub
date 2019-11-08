package commands;

public class Error401Report extends ServerCommand {
    private String errorMessage;
    public Error401Report(String msg) { errorMessage = msg; }

    public void call() {
        System.err.println("401 " + errorMessage);
    }

}
