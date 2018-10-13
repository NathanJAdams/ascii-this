package com.repocleaner.coreclean.transform.commands;

import com.repocleaner.coreclean.graph.Graph;
import com.repocleaner.coreclean.graph.PropertyKey;
import com.repocleaner.coreclean.graph.Vertex;
import com.repocleaner.coreclean.transform.TransformCommand;

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
