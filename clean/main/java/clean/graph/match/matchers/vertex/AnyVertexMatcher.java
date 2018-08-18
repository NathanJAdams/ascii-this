package clean.graph.match.matchers.vertex;

import clean.graph.Vertex;
import clean.graph.match.VertexMatcher;

public class AnyVertexMatcher implements VertexMatcher {
    private final String captureId;

    public AnyVertexMatcher() {
        this(null);
    }

    public AnyVertexMatcher(String captureId) {
        this.captureId = captureId;
    }

    @Override
    public String getCaptureId() {
        return captureId;
    }

    @Override
    public boolean isMatch(Vertex vertex) {
        return true;
    }
}
