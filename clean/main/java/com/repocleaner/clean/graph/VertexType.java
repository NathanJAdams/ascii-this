package com.repocleaner.clean.graph;

public enum VertexType implements GraphElementType {
    Repo,
    Folder,
    File,
    SourceRule,
    SourceNode;

    @Override
    public String getName() {
        return name();
    }
}
