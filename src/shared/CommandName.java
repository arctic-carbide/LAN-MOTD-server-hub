package shared;

public enum CommandName {
    MSGGET("MSGGET"),
    MSGSTORE("MSGSTORE"),
    LOGIN("LOGIN"),
    LOGOUT("LOGOUT"),
    SHUTDOWN("SHUTDOWN"),
    QUIT("QUIT"),
    SEND("SEND"),
    WHO("WHO")
    ;

    private final String value;
    CommandName(String str) {
        value = str;
    }
}
