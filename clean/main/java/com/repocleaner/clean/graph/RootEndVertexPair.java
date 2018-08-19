package com.repocleaner.clean.graph;

public class RootEndVertexPair {
    private final Vertex rootVertex;
    private final Vertex endVertex;

    public RootEndVertexPair(Vertex rootVertex, Vertex endVertex) {
        this.rootVertex = rootVertex;
        this.endVertex = endVertex;
    }

    public Vertex getRootVertex() {
        return rootVertex;
    }

    public Vertex getEndVertex() {
        return endVertex;
    }
}
