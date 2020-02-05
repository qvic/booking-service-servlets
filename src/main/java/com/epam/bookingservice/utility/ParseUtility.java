package com.epam.bookingservice.utility;

public final class ParseUtility {

    private ParseUtility() {

    }

    public static long parseLongOrDefault(String number, long defaultValue) {
        try {
            return Long.parseLong(number);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
