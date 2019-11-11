package shared;

import javafx.application.Application;

import java.io.*;
import java.lang.reflect.Constructor;
import java.net.Socket;

public class Utility {
    private static final boolean ENABLED = true;
    private static BufferedReader stdInput = new BufferedReader(new InputStreamReader(System.in));

    public static void display(String msg) {
        if (ENABLED) {
            System.out.println(msg);
        }
    }

    public static String getUserInput() throws Exception {
        return stdInput.readLine();
    }
//
//    public static <T> T wrapStream(InputStream istream, Class<T> tClass) throws Exception {
//        Constructor<T> constructor = tClass.getConstructor(istream.getClass());
//        T wrap = constructor.newInstance(istream);
//
//        return wrap;
//    }
//
//    public static <T> T wrapStream(OutputStream ostream, Class<T> tClass) throws  Exception {
//        Constructor<T> constructor = tClass.getConstructor(ostream.getClass());
//        T wrap = constructor.newInstance(ostream);
//
//        return wrap;
//    }
}
