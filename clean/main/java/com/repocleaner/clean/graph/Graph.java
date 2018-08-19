package com.repocleaner.clean.graph;

import org.jgrapht.graph.DefaultDirectedGraph;

import java.util.stream.Stream;

public class Graph extends DefaultDirectedGraph<Vertex, Edge> {
    public Graph() {
        super(null, null, false);
    }

    public Vertex firstOrNull(Vertex vertex, EdgeDirection edgeDirection, EdgeType edgeType) {
        return nextVertices(vertex, edgeDirection, edgeType)
                .findFirst()
                .orElse(null);
    }

    public Stream<Vertex> nextVertices(Vertex vertex, EdgeDirection edgeDirection, EdgeType edgeType) {
        return edgeDirection.getEdges(this, vertex)
                .stream()
                .filter(edge -> edge.isPropertyEquals(PropertyKeys.TYPE, edgeType))
                .map(edge -> edgeDirection.getOtherVertex(this, edge, vertex));
    }
}
