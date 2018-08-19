package com.repocleaner.clean.graph.match;

import com.repocleaner.clean.graph.Graph;
import com.repocleaner.clean.graph.Vertex;

import java.util.function.Function;
import java.util.stream.Stream;

public interface VertexMatcher extends GraphElementMatcher<Vertex>, Function<Graph, Stream<Vertex>> {
    boolean isMatch(Vertex vertex);

    default Stream<Vertex> apply(Graph graph) {
        return graph.vertexSet()
                .stream()
                .filter(this::isMatch);
    }
}
