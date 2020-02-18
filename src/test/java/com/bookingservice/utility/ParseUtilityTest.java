package com.bookingservice.utility;

import org.junit.Test;

import static org.junit.Assert.*;

public class ParseUtilityTest {

    @Test
    public void parseLongOrDefaultShouldParseLongWhenCorrectNumberIsPassed() {
        long value = ParseUtility.parseLongOrDefault("123", 321);
        assertEquals(123, value);
    }

    @Test
    public void parseLongOrDefaultShouldReturnDefaultWhenIncorrectNumberIsPassed() {
        long value = ParseUtility.parseLongOrDefault("abc", 321);
        assertEquals(321, value);
    }

    // test null
}