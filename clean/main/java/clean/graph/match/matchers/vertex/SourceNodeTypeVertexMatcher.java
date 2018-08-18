package clean.graph.match.matchers.vertex;

import clean.graph.VertexType;

public class SourceNodeTypeVertexMatcher extends SourceTypeVertexMatcher {
    public SourceNodeTypeVertexMatcher(int nodeSourceType) {
        super(VertexType.SourceNode, nodeSourceType);
    }

    public SourceNodeTypeVertexMatcher(String captureId, int nodeSourceType) {
        super(captureId, VertexType.SourceNode, nodeSourceType);
    }
}
