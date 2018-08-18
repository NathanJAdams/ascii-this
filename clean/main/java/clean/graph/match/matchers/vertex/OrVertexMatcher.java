package clean.graph.match.matchers.vertex;

import clean.graph.Vertex;
import clean.graph.match.VertexMatcher;
import clean.util.LogicUtil;

public class OrVertexMatcher implements VertexMatcher {
    private final String captureId;
    private final VertexMatcher[] matchers;

    public OrVertexMatcher(VertexMatcher... matchers) {
        this(null, matchers);
    }

    public OrVertexMatcher(String captureId, VertexMatcher... matchers) {
        this.captureId = captureId;
        this.matchers = matchers;
    }

    @Override
    public String getCaptureId() {
        return captureId;
    }

    @Override
    public boolean isMatch(Vertex vertex) {
        return LogicUtil.or(matcher -> matcher.isMatch(vertex), matchers);
    }
}
