package com.repocleaner.coreclean.graph.match.matchers.vertex;

import com.repocleaner.coreclean.graph.VertexType;

public class BranchSourceTypeVertexMatcher extends SourceTypeVertexMatcher {
    public BranchSourceTypeVertexMatcher(String branchType) {
        super(VertexType.SourceBranch, branchType);
    }

    public BranchSourceTypeVertexMatcher(String captureId, String branchType) {
        super(captureId, VertexType.SourceBranch, branchType);
    }
}
