package com.repocleaner.clean.util;

import java.util.function.Predicate;

public class LogicUtil {
    public static <T> boolean and(Predicate<T> predicate, T[] array) {
        for (T t : array) {
            if (!predicate.test(t)) {
                return false;
            }
        }
        return true;
    }

    public static <T> boolean or(Predicate<T> predicate, T[] array) {
        for (T t : array) {
            if (predicate.test(t)) {
                return true;
            }
        }
        return false;
    }
}
