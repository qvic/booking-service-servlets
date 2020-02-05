package com.epam.bookingservice.utility;

import org.junit.Test;

import static com.epam.bookingservice.utility.ParseUtility.parseLongOrDefault;
import static org.junit.Assert.*;

public class ParseUtilityTest {

    @Test
    public void parseLongOrDefaultShouldParseLongWhenCorrectNumberIsPassed() {
        long value = parseLongOrDefault("123", 321);
        assertEquals(123, value);
    }

    @Test
    public void parseLongOrDefaultShouldReturnDefaultWhenIncorrectNumberIsPassed() {
        long value = parseLongOrDefault("abc", 321);
        assertEquals(321, value);
    }

    // test null
}