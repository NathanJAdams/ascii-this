package com.repocleaner.clean.graph;

public enum EdgeType implements GraphElementType {
    RepoContainsFile,
    RepoContainsFolder,
    FolderContainsFile,
    Child,
    FirstChild,
    NextSibling;

    @Override
    public String getName() {
        return name();
    }
}
