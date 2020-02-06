package com.epam.bookingservice.utility;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public final class ParseUtility {

    private ParseUtility() {

    }

    public static long parseLongOrDefault(String number, long defaultValue) {
        if (number == null) {
            return defaultValue;
        }

        try {
            return Long.parseLong(number);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static LocalDate parseLocalDateOrDefault(String date, LocalDate defaultDate) {
        if (date == null) {
            return defaultDate;
        }

        try {
            return LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            return defaultDate;
        }
    }
}
