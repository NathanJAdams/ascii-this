package com.repocleaner.clean.graph.match;

import com.repocleaner.clean.graph.Graph;
import com.repocleaner.clean.graph.RootEndVertexPair;
import com.repocleaner.clean.graph.Vertex;
import com.repocleaner.util.CalculatedIterator;
import com.repocleaner.util.StreamUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class RecursiveMatcher implements Function<Graph, Stream<RootEndVertexPair>> {
    private final VertexMatcher rootMatcher;
    private final SubPathMatcher subPathMatcher;
    private final VertexMatcher stopMatcher;

    public RecursiveMatcher(VertexMatcher rootMatcher, SubPathMatcher subPathMatcher, VertexMatcher stopMatcher) {
        this.rootMatcher = rootMatcher;
        this.subPathMatcher = subPathMatcher;
        this.stopMatcher = stopMatcher;
    }

    public Stream<RootEndVertexPair> apply(Graph graph) {
        return StreamUtil.stream(iterable(graph));
    }

    public Iterable<RootEndVertexPair> iterable(Graph graph) {
        return () -> new CalculatedIterator<RootEndVertexPair>() {
            Iterator<Vertex> rootVertexIterator = graph.vertexSet().iterator();
            Vertex rootVertex = getNextRootVertex();
            List<Iterator<SubPath>> subPathIterators = new ArrayList<>();
            List<SubPath> subPaths = new ArrayList<>();

            @Override
            public RootEndVertexPair calculateNext(int index, RootEndVertexPair previous) {
                while (rootVertex != null) {
                    if (getNext()) {
                        return capture();
                    }
                    while (!subPaths.isEmpty()) {
                        if (getLast()) {
                            return capture();
                        }
                    }
                    rootVertex = getNextRootVertex();
                }
                return null;
            }

            private Vertex getNextRootVertex() {
                while (rootVertexIterator.hasNext()) {
                    Vertex vertex = rootVertexIterator.next();
                    if (rootMatcher.isMatch(vertex)) {
                        return vertex;
                    }
                }
                return null;
            }

            private boolean getNext() {
                Vertex previousVertex = rootVertex;
                int lastIndex = subPaths.size() - 1;
                if (lastIndex != -1) {
                    previousVertex = subPaths.get(lastIndex).getVertex();
                }
                Iterator<SubPath> subPathIterator = subPathMatcher.iterable(graph, previousVertex).iterator();
                boolean success = subPathIterator.hasNext();
                if (success) {
                    SubPath next = getNextSubPath(subPathIterator);
                    if (next == null) {
                        return false;
                    }
                    subPathIterators.add(subPathIterator);
                    subPaths.add(next);
                }
                return success;
            }

            private boolean getLast() {
                int lastIndex = subPaths.size() - 1;
                if (lastIndex == -1) {
                    return false;
                }
                Iterator<SubPath> subPathIterator = subPathIterators.get(lastIndex);
                boolean success = subPathIterator.hasNext();
                if (success) {
                    SubPath next = getNextSubPath(subPathIterator);
                    if (next == null) {
                        subPathIterators.remove(lastIndex);
                        subPaths.remove(lastIndex);
                        return false;
                    }
                    subPaths.set(lastIndex, next);
                } else {
                    subPathIterators.remove(lastIndex);
                    subPaths.remove(lastIndex);
                }
                return success;
            }

            private SubPath getNextSubPath(Iterator<SubPath> subPathIterator) {
                while (subPathIterator.hasNext()) {
                    SubPath next = subPathIterator.next();
                    Vertex nextVertex = next.getVertex();
                    if (!stopMatcher.isMatch(nextVertex)) {
                        return next;
                    }
                }
                return null;
            }

            private RootEndVertexPair capture() {
                int lastIndex = subPaths.size() - 1;
                Vertex endVertex = subPaths.get(lastIndex).getVertex();
                return new RootEndVertexPair(rootVertex, endVertex);
            }
        };
    }
}
