package com.salon.booking.utility;

import java.util.List;
import java.util.OptionalInt;
import java.util.function.Predicate;

public final class ListUtility {

    private ListUtility() {

    }

    public static <E> OptionalInt getIndexOfFirstMatch(List<E> list, Predicate<E> predicate) {
        for (int i = 0; i < list.size(); i++) {
            if (predicate.test(list.get(i))) {
                return OptionalInt.of(i);
            }
        }
        return OptionalInt.empty();
    }
}
