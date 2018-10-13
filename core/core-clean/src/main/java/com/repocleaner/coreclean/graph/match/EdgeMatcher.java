package com.repocleaner.coreclean.graph.match;

import com.repocleaner.coreclean.graph.Edge;
import com.repocleaner.coreclean.graph.EdgeDirection;
import com.repocleaner.coreclean.graph.Graph;
import com.repocleaner.coreclean.graph.Vertex;

import java.util.function.BiFunction;
import java.util.stream.Stream;

public interface EdgeMatcher extends GraphElementMatcher<Edge>, BiFunction<Graph, Vertex, Stream<Edge>> {
    EdgeDirection getEdgeDirection();

    boolean isMatch(Edge edge);

    default Stream<Edge> apply(Graph graph, Vertex vertex) {
        return getEdgeDirection()
                .getEdges(graph, vertex)
                .stream()
                .filter(this::isMatch);
    }
}
