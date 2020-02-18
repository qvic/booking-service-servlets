package com.bookingservice.utility;

import java.util.Collections;
import java.util.List;

public final class CollectionUtility {

    private CollectionUtility() {
    }

    public static <T> List<T> nullSafeListInitialize(List<T> list) {
        return list == null ? Collections.emptyList() : Collections.unmodifiableList(list);
    }
}
