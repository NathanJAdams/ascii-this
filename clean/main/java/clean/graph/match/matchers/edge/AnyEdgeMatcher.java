package clean.graph.match.matchers.edge;

import clean.graph.Edge;
import clean.graph.EdgeDirection;
import clean.graph.match.EdgeMatcher;

public class AnyEdgeMatcher implements EdgeMatcher {
    private final String captureId;

    public AnyEdgeMatcher() {
        this(null);
    }

    public AnyEdgeMatcher(String captureId) {
        this.captureId = captureId;
    }

    @Override
    public String getCaptureId() {
        return captureId;
    }

    @Override
    public EdgeDirection getEdgeDirection() {
        return EdgeDirection.Both;
    }

    @Override
    public boolean isMatch(Edge edge) {
        return true;
    }
}
