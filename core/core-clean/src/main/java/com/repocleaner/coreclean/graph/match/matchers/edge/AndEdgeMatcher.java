package com.repocleaner.coreclean.graph.match.matchers.edge;

import com.repocleaner.coreclean.graph.Edge;
import com.repocleaner.coreclean.graph.EdgeDirection;
import com.repocleaner.coreclean.graph.match.EdgeMatcher;
import com.repocleaner.util.LogicUtil;

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
