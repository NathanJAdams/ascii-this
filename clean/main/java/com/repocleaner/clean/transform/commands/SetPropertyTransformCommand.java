package com.repocleaner.clean.transform.commands;

import com.repocleaner.clean.graph.Graph;
import com.repocleaner.clean.graph.PropertyKey;
import com.repocleaner.clean.graph.Vertex;
import com.repocleaner.clean.transform.TransformCommand;

public class SetPropertyTransformCommand<T> implements TransformCommand {
    private final Vertex vertex;
    private final PropertyKey<T> key;
    private final T newValue;
    private final T previousValue;

    public SetPropertyTransformCommand(Vertex vertex, PropertyKey<T> propertyKey, T propertyValue) {
        this.vertex = vertex;
        this.key = propertyKey;
        this.newValue = propertyValue;
        this.previousValue = vertex.getProperty(key);
    }

    @Override
    public void execute(Graph graph) {
        vertex.setProperty(key, newValue);
    }

    @Override
    public void undo(Graph graph) {
        vertex.setProperty(key, previousValue);
    }
}
