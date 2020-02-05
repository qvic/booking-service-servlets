package com.epam.bookingservice.utility;

public final class StringUtility {

    private StringUtility() {

    }

    public static boolean nullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }

    public static boolean longerThan(String string, int length) {
        return string.length() > length;
    }

    public static boolean shorterThan(String string, int length) {
        return string.length() < length;
    }
}
