package com.epam.bookingservice.utility;

import org.junit.Test;

import static org.junit.Assert.*;

public class StringUtilityTest {

    @Test
    public void testNullOrEmpty() {
        assertTrue(StringUtility.nullOrEmpty(null));
        assertTrue(StringUtility.nullOrEmpty(""));
    }

    @Test
    public void testNotNullOrEmpty() {
        assertFalse(StringUtility.nullOrEmpty("a"));
    }

    @Test
    public void testLongerThan() {
        assertTrue(StringUtility.longerThan("abc", 2));
        assertFalse(StringUtility.longerThan("abc", 3));
    }

    @Test
    public void testShorterThan() {
        assertTrue(StringUtility.shorterThan("abc", 4));
        assertFalse(StringUtility.shorterThan("abc", 3));
    }
}