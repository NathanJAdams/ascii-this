package com.repocleaner.clean.graph.match;

import com.repocleaner.clean.graph.Edge;
import com.repocleaner.clean.graph.Vertex;

public class SubPath {
    private final Edge edge;
    private final Vertex vertex;

    public SubPath(Edge edge, Vertex vertex) {
        this.edge = edge;
        this.vertex = vertex;
    }

    public Edge getEdge() {
        return edge;
    }

    public Vertex getVertex() {
        return vertex;
    }
}
