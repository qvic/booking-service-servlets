package com.salon.booking.utility;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class ParseUtilityTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

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

    @Test
    public void parseLongOrDefaultShouldReturnDefaultWhenNullIsPassed() {
        long value = ParseUtility.parseLongOrDefault(null, 321);
        assertEquals(321, value);
    }

    @Test
    public void parseLocalDateOrDefaultShouldReturnCorrectValue() {
        String stringDate = "2020-10-09";
        LocalDate defaultDate = LocalDate.of(2020, 9, 8);

        LocalDate actual = ParseUtility.parseLocalDateOrDefault(stringDate, defaultDate);

        assertEquals(LocalDate.of(2020, 10, 9), actual);
    }

    @Test
    public void parseLocalDateOrDefaultShouldReturnDefaultValueWhenFormatIsInvalid() {
        String stringDate = "2020:10:1";
        LocalDate defaultDate = LocalDate.of(2020, 9, 8);

        LocalDate actual = ParseUtility.parseLocalDateOrDefault(stringDate, defaultDate);

        assertEquals(defaultDate, actual);
    }

    @Test
    public void parseLocalDateOrDefaultShouldReturnDefaultValueWhenStringIsNull() {
        LocalDate defaultDate = LocalDate.of(2020, 9, 8);

        LocalDate actual = ParseUtility.parseLocalDateOrDefault(null, defaultDate);

        assertEquals(defaultDate, actual);
    }

    @Test
    public void parseIntOrThrowShouldParseIntWhenValueIsCorrect() {
        assertEquals(123, ParseUtility.parseIntOrThrow("123", RuntimeException::new));
    }

    @Test
    public void parseIntOrThrowShouldThrowWhenValueIsIncorrect() {
        expectedException.expect(RuntimeException.class);
        ParseUtility.parseIntOrThrow("a123", RuntimeException::new);
    }
}