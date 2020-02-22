package com.salon.booking.utility;

import org.junit.Test;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class StreamUtilityTest {

    @Test
    public void toStreamShouldReturnSingletonStreamIfOptionalIsPresent() {
        Optional<String> optional = Optional.of("test");

        assertEquals(1, StreamUtility.toStream(optional).count());
    }

    @Test
    public void toStreamShouldReturnEmptyStreamIfOptionalIsEmpty() {
        Optional<String> optional = Optional.empty();

        assertEquals(0, StreamUtility.toStream(optional).count());
    }
}