package com.repocleaner.coreclean.graph.match.matchers.vertex;

import com.repocleaner.coreclean.graph.VertexType;

public class EofVertexMatcher extends TypeVertexMatcher {
    public EofVertexMatcher() {
        super(VertexType.SourceEof);
    }

    public EofVertexMatcher(String captureId) {
        super(captureId, VertexType.SourceEof);
    }
}
