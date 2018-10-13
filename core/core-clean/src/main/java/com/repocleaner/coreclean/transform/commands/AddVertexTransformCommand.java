package com.repocleaner.coreclean.transform.commands;

import com.repocleaner.coreclean.graph.Graph;
import com.repocleaner.coreclean.graph.Vertex;
import com.repocleaner.coreclean.transform.TransformCommand;

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
