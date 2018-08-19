package com.repocleaner.clean.transform.commands;

import com.repocleaner.clean.graph.Edge;
import com.repocleaner.clean.graph.Graph;
import com.repocleaner.clean.graph.Vertex;
import com.repocleaner.clean.transform.TransformCommand;

public class RemoveEdgeTransformCommand implements TransformCommand {
    private final Vertex sourceVertex;
    private final Vertex targetVertex;
    private final Edge edge;

    public RemoveEdgeTransformCommand(Vertex sourceVertex, Vertex targetVertex, Edge edge) {
        this.sourceVertex = sourceVertex;
        this.targetVertex = targetVertex;
        this.edge = edge;
    }

    @Override
    public void execute(Graph graph) {
        graph.removeEdge(edge);
    }

    @Override
    public void undo(Graph graph) {
        graph.addEdge(sourceVertex, targetVertex, edge);
    }
}
