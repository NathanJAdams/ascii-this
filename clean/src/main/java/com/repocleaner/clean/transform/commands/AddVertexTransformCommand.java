package com.repocleaner.clean.transform.commands;

import com.repocleaner.clean.graph.Graph;
import com.repocleaner.clean.graph.Vertex;
import com.repocleaner.clean.transform.TransformCommand;

public class AddVertexTransformCommand implements TransformCommand {
    private final Vertex vertex;

    public AddVertexTransformCommand(Vertex vertex) {
        this.vertex = vertex;
    }

    @Override
    public void execute(Graph graph) {
        graph.addVertex(vertex);
    }

    @Override
    public void undo(Graph graph) {
        graph.removeVertex(vertex);
    }
}
