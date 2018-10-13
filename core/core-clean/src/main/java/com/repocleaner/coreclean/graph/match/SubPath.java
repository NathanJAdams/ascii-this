package com.repocleaner.coreclean.graph.match;

import com.repocleaner.coreclean.graph.Edge;
import com.repocleaner.coreclean.graph.Vertex;

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
