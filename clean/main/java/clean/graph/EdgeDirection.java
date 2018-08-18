package clean.graph;

import java.util.Set;

public enum EdgeDirection {
    Incoming {
        @Override
        public Set<Edge> getEdges(Graph graph, Vertex vertex) {
            return graph.incomingEdgesOf(vertex);
        }

        @Override
        public Vertex getOtherVertex(Graph graph, Edge edge, Vertex vertex) {
            return graph.getEdgeSource(edge);
        }
    },
    Outgoing {
        @Override
        public Set<Edge> getEdges(Graph graph, Vertex vertex) {
            return graph.outgoingEdgesOf(vertex);
        }

        @Override
        public Vertex getOtherVertex(Graph graph, Edge edge, Vertex vertex) {
            return graph.getEdgeTarget(edge);
        }
    },
    Both {
        @Override
        public Set<Edge> getEdges(Graph graph, Vertex vertex) {
            return graph.edgesOf(vertex);
        }

        @Override
        public Vertex getOtherVertex(Graph graph, Edge edge, Vertex vertex) {
            Vertex edgeSource = graph.getEdgeSource(edge);
            Vertex edgeTarget = graph.getEdgeTarget(edge);
            return (edgeSource == vertex)
                    ? edgeTarget
                    : edgeSource;
        }
    };

    public abstract Set<Edge> getEdges(Graph graph, Vertex vertex);

    public abstract Vertex getOtherVertex(Graph graph, Edge edge, Vertex vertex);
}
