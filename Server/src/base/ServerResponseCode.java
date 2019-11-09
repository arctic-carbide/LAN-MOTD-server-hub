package base;

public enum ServerResponseCode {
    OK ("200 OK"),
    FAIL ("401 ");

    private final String VALUE;

    ServerResponseCode(String str) {
        VALUE = str;
    }

    public String getVALUE() { return VALUE; }
}
