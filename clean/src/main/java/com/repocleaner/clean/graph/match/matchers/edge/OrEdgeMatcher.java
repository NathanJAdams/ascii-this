package com.repocleaner.clean.graph.match.matchers.edge;

import com.repocleaner.clean.graph.Edge;
import com.repocleaner.clean.graph.EdgeDirection;
import com.repocleaner.clean.graph.match.EdgeMatcher;
import com.repocleaner.util.LogicUtil;

public class OrEdgeMatcher implements EdgeMatcher {
    private final String captureId;
    private final EdgeDirection edgeDirection;
    private final EdgeMatcher[] matchers;

    public OrEdgeMatcher(EdgeDirection edgeDirection, EdgeMatcher... matchers) {
        this(null, edgeDirection, matchers);
    }

    public OrEdgeMatcher(String captureId, EdgeDirection edgeDirection, EdgeMatcher... matchers) {
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
        return LogicUtil.or(matcher -> matcher.isMatch(edge), matchers);
    }
}
