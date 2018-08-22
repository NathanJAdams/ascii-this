package com.repocleaner.clean.transform.commands;

import com.repocleaner.clean.graph.Edge;
import com.repocleaner.clean.graph.Graph;
import com.repocleaner.clean.graph.Vertex;
import com.repocleaner.clean.transform.TransformCommand;

public class AddEdgeTransformCommand implements TransformCommand {
    private final Vertex sourceVertex;
    private final Vertex targetVertex;
    private final Edge edge;

    public AddEdgeTransformCommand(Vertex sourceVertex, Vertex targetVertex, Edge edge) {
        this.sourceVertex = sourceVertex;
        this.targetVertex = targetVertex;
        this.edge = edge;
    }

    @Override
    public void execute(Graph graph) {
        graph.addEdge(sourceVertex, targetVertex, edge);
    }

    @Override
    public void undo(Graph graph) {
        graph.removeEdge(edge);
    }
}
