package com.salon.booking.utility;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EnumUtilityTest {

    private enum TestEnum {
        V1, V2, V3
    }

    @Test
    public void findByNameShouldReturnCorrectValue() {
        Optional<TestEnum> value = EnumUtility.findByName("V2", TestEnum.values());

        assertTrue(value.isPresent());
        assertEquals(TestEnum.V2, value.get());
    }

    @Test
    public void findByNameShouldReturnNullOnIncorrectParameter() {
        Optional<TestEnum> value = EnumUtility.findByName("A2", TestEnum.values());

        assertFalse(value.isPresent());
    }

    @Test
    public void findByNameShouldReturnNullOnNullParameter() {
        Optional<TestEnum> value = EnumUtility.findByName(null, TestEnum.values());

        assertFalse(value.isPresent());
    }
}