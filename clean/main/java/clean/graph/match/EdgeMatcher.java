package clean.graph.match;

import clean.graph.Edge;
import clean.graph.EdgeDirection;
import clean.graph.Graph;
import clean.graph.Vertex;

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
