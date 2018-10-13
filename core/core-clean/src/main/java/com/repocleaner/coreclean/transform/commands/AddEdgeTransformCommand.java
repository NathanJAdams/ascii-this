package com.repocleaner.coreclean.transform.commands;

import com.repocleaner.coreclean.graph.Edge;
import com.repocleaner.coreclean.graph.Graph;
import com.repocleaner.coreclean.graph.Vertex;
import com.repocleaner.coreclean.transform.TransformCommand;

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
