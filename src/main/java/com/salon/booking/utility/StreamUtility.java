package com.salon.booking.utility;

import java.util.Optional;
import java.util.stream.Stream;

public final class StreamUtility {

    private StreamUtility() {

    }

    public static <T> Stream<T> toStream(Optional<T> o) {
        return o.map(Stream::of).orElseGet(Stream::empty);
    }
}
