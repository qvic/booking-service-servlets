package com.bookingservice.utility;

public final class StringUtility {

    private StringUtility() {

    }

    public static boolean nullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }

    public static boolean longerThan(int length, String string) {
        return string.length() > length;
    }

    public static boolean shorterThan(int length, String string) {
        return string.length() < length;
    }
}
