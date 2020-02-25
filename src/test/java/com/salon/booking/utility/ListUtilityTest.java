package com.salon.booking.utility;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ListUtilityTest {

    @Test
    public void getIndexOfFirstMatchShoulReturnCorrectIndex() {
        List<Integer> list = Arrays.asList(123, 124, 125, 126);

        OptionalInt indexOfFirstMatch = ListUtility.getIndexOfFirstMatch(list, i -> i == 124);

        assertTrue(indexOfFirstMatch.isPresent());
        assertEquals(1, indexOfFirstMatch.getAsInt());
    }

    @Test
    public void getIndexOfFirstMatchShoulReturnEmptyOptionalWhenNoSuchElementFound() {
        List<Integer> list = Arrays.asList(123, 124, 125, 126);

        OptionalInt indexOfFirstMatch = ListUtility.getIndexOfFirstMatch(list, i -> i == 100);

        assertFalse(indexOfFirstMatch.isPresent());
    }
}