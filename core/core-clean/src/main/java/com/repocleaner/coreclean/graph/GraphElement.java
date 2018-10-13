package com.repocleaner.coreclean.graph;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

public abstract class GraphElement {
    private final Map<PropertyKey<?>, Object> properties = new HashMap<>();

    public GraphElement(GraphElementType type) {
        setProperty(PropertyKeys.TYPE, type);
    }

    @SuppressWarnings("unchecked")
    public <T> T setProperty(PropertyKey<T> key, T property) {
        Object previousProperty = properties.put(key, property);
        return (T) previousProperty;
    }

    @SuppressWarnings("unchecked")
    public <T> T getProperty(PropertyKey<T> key) {
        return (T) properties.get(key);
    }

    public boolean hasProperty(PropertyKey<?> key) {
        return properties.containsKey(key);
    }

    public <T> boolean hasPropertyAnd(PropertyKey<T> key, Predicate<T> valuePredicate) {
        T value = getProperty(key);
        return (value != null)
                && valuePredicate.test(value);
    }

    public <T> boolean isPropertyEquals(PropertyKey<T> key, T property) {
        Object actualProperty = properties.get(key);
        return Objects.equals(actualProperty, property);
    }

    @Override
    public String toString() {
        return properties.toString();
    }
}
