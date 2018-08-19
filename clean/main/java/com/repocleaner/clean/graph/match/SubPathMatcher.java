package com.repocleaner.clean.graph.match;

import com.repocleaner.clean.graph.Edge;
import com.repocleaner.clean.graph.Graph;
import com.repocleaner.clean.graph.Vertex;
import com.repocleaner.clean.graph.match.matchers.edge.AnyEdgeMatcher;
import com.repocleaner.clean.graph.match.matchers.vertex.AnyVertexMatcher;
import com.repocleaner.clean.util.CalculatedIterator;
import com.repocleaner.clean.util.StreamUtil;

import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public class SubPathMatcher implements BiFunction<Graph, Vertex, Stream<SubPath>> {
    private final EdgeMatcher edgeMatcher;
    private final VertexMatcher vertexMatcher;

    public SubPathMatcher() {
        this(new AnyEdgeMatcher(), new AnyVertexMatcher());
    }

    public SubPathMatcher(EdgeMatcher edgeMatcher) {
        this(edgeMatcher, new AnyVertexMatcher());
    }

    public SubPathMatcher(VertexMatcher vertexMatcher) {
        this(new AnyEdgeMatcher(), vertexMatcher);
    }

    public SubPathMatcher(EdgeMatcher edgeMatcher, VertexMatcher vertexMatcher) {
        this.edgeMatcher = edgeMatcher;
        this.vertexMatcher = vertexMatcher;
    }

    public Stream<SubPath> apply(Graph graph, Vertex root) {
        return StreamUtil.stream(iterable(graph, root));
    }

    public Iterable<SubPath> iterable(Graph graph, Vertex root) {
        return () -> new CalculatedIterator<SubPath>() {
            Iterator<Edge> edgeIterator = edgeMatcher.getEdgeDirection().getEdges(graph, root).iterator();

            @Override
            public SubPath calculateNext(int index, SubPath previous) {
                while (edgeIterator.hasNext()) {
                    Edge edge = edgeIterator.next();
                    Vertex vertex = edgeMatcher.getEdgeDirection().getOtherVertex(graph, edge, root);
                    if (vertexMatcher.isMatch(vertex)) {
                        return new SubPath(edge, vertex);
                    }
                }
                return null;
            }
        };
    }

    public void capture(CaptureGroup captureGroup, SubPath subPath) {
        String edgeCaptureId = edgeMatcher.getCaptureId();
        String vertexCaptureId = vertexMatcher.getCaptureId();
        if (edgeCaptureId != null) {
            captureGroup.captureEdge(edgeCaptureId, subPath.getEdge());
        }
        if (vertexCaptureId != null) {
            captureGroup.captureVertex(vertexCaptureId, subPath.getVertex());
        }
    }
}
