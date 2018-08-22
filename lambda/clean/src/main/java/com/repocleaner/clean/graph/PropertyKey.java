package com.repocleaner.clean.graph;

public class PropertyKey<T> {
    private final String name;

    public PropertyKey(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
