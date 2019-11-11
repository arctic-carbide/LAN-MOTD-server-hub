package shared;

public enum ServerResponseCode {
    OK ("200 OK"),
    FAIL ("401 ");

    public final String VALUE;

    ServerResponseCode(String str) {
        VALUE = str;
    }
}
