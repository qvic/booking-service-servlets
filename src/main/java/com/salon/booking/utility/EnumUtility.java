package com.salon.booking.utility;

import java.util.Arrays;
import java.util.Optional;

public final class EnumUtility {

    private EnumUtility() {

    }

    public static <T extends Enum<T>> Optional<T> findByName(String name, T[] enumValues) {
        return Arrays.stream(enumValues)
                .filter(value -> value.name().equals(name))
                .findAny();
    }
}
