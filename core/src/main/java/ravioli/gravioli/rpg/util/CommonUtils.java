package ravioli.gravioli.rpg.util;

public class CommonUtils {
    public static boolean isInteger(String string) {
        return string.matches("^(-|\\+)?\\d+$");
    }
}
