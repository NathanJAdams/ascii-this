package com.repocleaner.clean.util;

import java.util.Iterator;

public abstract class CalculatedIterator<T> implements Iterator<T> {
    private int index;
    private boolean isNextValid;
    private T next;

    @Override
    public final boolean hasNext() {
        if (!isNextValid) {
            recalculateNext();
            isNextValid = true;
        }
        return (next != null);
    }

    @Override
    public final T next() {
        if (!isNextValid) {
            recalculateNext();
        }
        isNextValid = false;
        return next;
    }

    private void recalculateNext() {
        next = calculateNext(index, next);
        index++;
    }

    public abstract T calculateNext(int index, T previous);
}
