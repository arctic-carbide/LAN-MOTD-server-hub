package exceptions;

public enum ServerExceptionMessage {
    INSUFFICIENT_LOGIN_ARGUMENTS("Usage: LOGIN <username> <password>"),
    INVALID_LOGIN_ARGUMENTS("Username or Password"),
    INVALID_COMMAND("Command not recognized! Please try again."),
    UNAUTHORIZED("You are not currently logged in, log in first")
    ;


    private final String message;

    ServerExceptionMessage(String s) {
        message = s;
    }

    public String getMessage() { return message; }
}
