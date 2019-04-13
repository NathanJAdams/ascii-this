package com.repocleaner.coreclean.graph;

public enum VertexType implements GraphElementType {
    Repo,
    Folder,
    File,
    SourceBranch,
    SourceLeaf,
    SourceEof;

    @Override
    public String getName() {
        return name();
    }
}
