package clean.graph.match.matchers.edge;

import clean.graph.Edge;
import clean.graph.EdgeDirection;
import clean.graph.match.EdgeMatcher;
import clean.util.LogicUtil;

public class AndEdgeMatcher implements EdgeMatcher {
    private final String captureId;
    private final EdgeDirection edgeDirection;
    private final EdgeMatcher[] matchers;

    public AndEdgeMatcher(EdgeDirection edgeDirection, EdgeMatcher... matchers) {
        this(null, edgeDirection, matchers);
    }

    public AndEdgeMatcher(String captureId, EdgeDirection edgeDirection, EdgeMatcher... matchers) {
        this.captureId = captureId;
        this.edgeDirection = edgeDirection;
        this.matchers = matchers;
    }

    @Override
    public String getCaptureId() {
        return captureId;
    }

    @Override
    public EdgeDirection getEdgeDirection() {
        return edgeDirection;
    }

    @Override
    public boolean isMatch(Edge edge) {
        return LogicUtil.and(matcher -> matcher.isMatch(edge), matchers);
    }
}
