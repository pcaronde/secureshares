package ro.panzo.secureshares.util;

public class Util {
    private static Util ourInstance = new Util();

    public static Util getInstance() {
        return ourInstance;
    }

    private Util() {
    }
}
