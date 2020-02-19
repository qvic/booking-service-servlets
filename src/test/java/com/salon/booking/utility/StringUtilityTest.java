package com.salon.booking.utility;

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
        assertTrue(StringUtility.longerThan(2, "abc"));
        assertFalse(StringUtility.longerThan(3, "abc"));
    }

    @Test
    public void testShorterThan() {
        assertTrue(StringUtility.shorterThan(4, "abc"));
        assertFalse(StringUtility.shorterThan(3, "abc"));
    }
}