package com.repocleaner.coreclean.graph.match.matchers.vertex;

import com.repocleaner.coreclean.graph.VertexType;

public class LeafSourceTypeVertexMatcher extends SourceTypeVertexMatcher {
    public LeafSourceTypeVertexMatcher(String leafSourceType) {
        super(VertexType.SourceLeaf, leafSourceType);
    }

    public LeafSourceTypeVertexMatcher(String captureId, String leafSourceType) {
        super(captureId, VertexType.SourceLeaf, leafSourceType);
    }
}
