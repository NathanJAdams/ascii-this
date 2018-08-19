package com.repocleaner.clean.transform.commands;

import com.repocleaner.clean.graph.Graph;
import com.repocleaner.clean.graph.Vertex;
import com.repocleaner.clean.transform.TransformCommand;

public class RemoveVertexTransformCommand implements TransformCommand {
    private final Vertex vertex;

    public RemoveVertexTransformCommand(Vertex vertex) {
        this.vertex = vertex;
    }

    @Override
    public void execute(Graph graph) {
        graph.removeVertex(vertex);
    }

    @Override
    public void undo(Graph graph) {
        graph.addVertex(vertex);
    }
}
