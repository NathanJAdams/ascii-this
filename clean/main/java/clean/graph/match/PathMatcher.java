package clean.graph.match;

import clean.graph.Graph;
import clean.graph.Vertex;
import clean.util.CalculatedIterator;
import clean.util.StreamUtil;

import java.util.Iterator;
import java.util.function.Function;
import java.util.stream.Stream;

public class PathMatcher implements Function<Graph, Stream<CaptureGroup>> {
    private final VertexMatcher rootMatcher;
    private final SubPathMatcher[] subPathMatchers;

    public PathMatcher(VertexMatcher rootMatcher, SubPathMatcher... subPathMatchers) {
        this.rootMatcher = rootMatcher;
        this.subPathMatchers = subPathMatchers;
    }

    public Stream<CaptureGroup> apply(Graph graph) {
        return StreamUtil.stream(iterable(graph));
    }

    public Iterable<CaptureGroup> iterable(Graph graph) {
        return () -> new CalculatedIterator<CaptureGroup>() {
            Iterator<Vertex> rootVertexIterator = graph.vertexSet().iterator();
            Vertex rootVertex = getNextRootVertex();
            @SuppressWarnings("unchecked")
            Iterator<SubPath>[] subPathIterators = new Iterator[subPathMatchers.length];
            SubPath[] subPaths = new SubPath[subPathMatchers.length];

            @Override
            public CaptureGroup calculateNext(int index, CaptureGroup previous) {
                return refillPath()
                        ? capture()
                        : null;
            }

            private boolean refillPath() {
                if (rootVertex == null) {
                    return false;
                }
                for (int index = subPaths.length - 1; ; ) {
                    while ((index >= 0) && (index < subPaths.length)) {
                        if (fillSubPath(index)) {
                            index++;
                        } else {
                            index--;
                        }
                    }
                    if (index == subPaths.length) {
                        return true;
                    }
                    rootVertex = getNextRootVertex();
                    if (rootVertex == null) {
                        return false;
                    }
                    index = 0;
                }
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

            private boolean fillSubPath(int index) {
                Iterator<SubPath> subPathIterator = subPathIterators[index];
                if (subPathIterator == null) {
                    Vertex previousVertex;
                    if (index == 0) {
                        previousVertex = rootVertex;
                    } else {
                        SubPath previousSubPath = subPaths[index - 1];
                        if (previousSubPath == null) {
                            return false;
                        }
                        previousVertex = previousSubPath.getVertex();
                    }
                    subPathIterator = subPathMatchers[index].iterable(graph, previousVertex).iterator();
                    subPathIterators[index] = subPathIterator;
                }
                if (!subPathIterator.hasNext()) {
                    subPathIterators[index] = null;
                    return false;
                }
                subPaths[index] = subPathIterator.next();
                return true;
            }

            private CaptureGroup capture() {
                CaptureGroup captureGroup = new CaptureGroup();
                String rootCaptureId = rootMatcher.getCaptureId();
                if (rootCaptureId != null) {
                    captureGroup.captureVertex(rootCaptureId, rootVertex);
                }
                for (int i = 0; i < subPathMatchers.length; i++) {
                    SubPathMatcher subPathMatcher = subPathMatchers[i];
                    SubPath subPath = subPaths[i];
                    subPathMatcher.capture(captureGroup, subPath);
                }
                return captureGroup;
            }
        };
    }
}
