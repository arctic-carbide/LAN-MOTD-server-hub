package shared;

public class Utility {
    private static final boolean ENABLED = true;

    public static void Display(String msg) {
        if (ENABLED) {
            System.out.println(msg);
        }
    }
}
