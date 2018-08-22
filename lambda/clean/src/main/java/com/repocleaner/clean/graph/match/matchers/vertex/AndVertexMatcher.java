package com.repocleaner.clean.graph.match.matchers.vertex;

import com.repocleaner.clean.graph.Vertex;
import com.repocleaner.clean.graph.match.VertexMatcher;
import com.repocleaner.util.LogicUtil;

public class AndVertexMatcher implements VertexMatcher {
    private final String captureId;
    private final VertexMatcher[] matchers;

    public AndVertexMatcher(VertexMatcher... matchers) {
        this(null, matchers);
    }

    public AndVertexMatcher(String captureId, VertexMatcher... matchers) {
        this.captureId = captureId;
        this.matchers = matchers;
    }

    @Override
    public String getCaptureId() {
        return captureId;
    }

    @Override
    public boolean isMatch(Vertex vertex) {
        return LogicUtil.and(matcher -> matcher.isMatch(vertex), matchers);
    }
}
