package util;

public class SecurityUtil {
    private SecurityUtil() {
        // Prevent instantiation
    }

    public static String sanitizeInput(String input) {
        return input.replaceAll("[^a-zA-Z0-9 ]", "");
    }
}
