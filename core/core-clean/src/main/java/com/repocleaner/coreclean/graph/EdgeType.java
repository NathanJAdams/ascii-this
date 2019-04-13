package com.repocleaner.coreclean.graph;

public enum EdgeType implements GraphElementType {
    RepoContainsFile,
    RepoContainsFolder,
    FolderContainsFile,
    Child,
    FirstChild,
    NextSibling,
    Eof;

    @Override
    public String getName() {
        return name();
    }
}
