package exceptions;

public enum ServerExceptionMessage {
    INSUFFICIENT_LOGIN_ARGUMENTS("Usage: LOGIN <username> <password>"),
    INVALID_LOGIN_ARGUMENTS("Username or Password"),
    INVALID_COMMAND("Command not recognized! Please try again."),
    UNAUTHORIZED_COMMAND("You are not currently logged in, log in first"),
    NULL_USER("Could not find user")
    ;

    public final String MESSAGE;
    ServerExceptionMessage(String s) {
        MESSAGE = s;
    }
}
