package by.bestlunch.util;

import org.springframework.lang.Nullable;

class Util {
    private Util() {
    }

    static <T extends Comparable<? super T>> boolean isBetweenInclusive(T value, @Nullable T start, @Nullable T end) {
        return (start == null || value.compareTo(start) >= 0) && (end == null || value.compareTo(end) <= 0);
    }
}