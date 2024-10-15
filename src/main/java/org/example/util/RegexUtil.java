package org.example.util;

public final class RegexUtil {

    /**
     * Phone number regex pattern.
     * Examples: +1234567890, 9876543210
     */
    public final static String PHONE_PATTERN = "^\\+?[1-9]\\d{5,14}$";


    private RegexUtil() {
        throw new RuntimeException();
    }

}
