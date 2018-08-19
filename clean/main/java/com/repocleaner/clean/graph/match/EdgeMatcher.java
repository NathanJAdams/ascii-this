package com.repocleaner.clean.graph.match;

import com.repocleaner.clean.graph.Edge;
import com.repocleaner.clean.graph.EdgeDirection;
import com.repocleaner.clean.graph.Graph;
import com.repocleaner.clean.graph.Vertex;

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
