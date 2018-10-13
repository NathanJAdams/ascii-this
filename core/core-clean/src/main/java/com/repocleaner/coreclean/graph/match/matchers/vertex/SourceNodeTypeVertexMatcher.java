package com.repocleaner.coreclean.graph.match.matchers.vertex;

import com.repocleaner.coreclean.graph.VertexType;

public class SourceNodeTypeVertexMatcher extends SourceTypeVertexMatcher {
    public SourceNodeTypeVertexMatcher(int nodeSourceType) {
        super(VertexType.SourceNode, nodeSourceType);
    }

    public SourceNodeTypeVertexMatcher(String captureId, int nodeSourceType) {
        super(captureId, VertexType.SourceNode, nodeSourceType);
    }
}
