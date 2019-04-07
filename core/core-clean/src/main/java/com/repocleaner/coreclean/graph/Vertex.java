package com.repocleaner.coreclean.graph;

public class Vertex extends GraphElement {
    private Vertex(GraphElementType vertexType) {
        super(vertexType);
    }

    public static Vertex createRepoVertex() {
        return new Vertex(VertexType.Repo);
    }

    public static Vertex createFolderVertex(String folderName) {
        Vertex vertex = new Vertex(VertexType.Folder);
        vertex.setProperty(PropertyKeys.FOLDER_NAME, folderName);
        return vertex;
    }

    public static Vertex createFileVertex(String fileName) {
        Vertex vertex = new Vertex(VertexType.File);
        vertex.setProperty(PropertyKeys.FILE_NAME, fileName);
        vertex.setProperty(PropertyKeys.FILE_HAS_CHANGED, false);
        return vertex;
    }

    public static Vertex createRuleVertex(String branchType) {
        Vertex vertex = new Vertex(VertexType.SourceBranch);
        vertex.setProperty(PropertyKeys.SOURCE_TYPE, branchType);
        return vertex;
    }

    public static Vertex createNodeVertex(String leafType, String text) {
        Vertex vertex = new Vertex(VertexType.SourceLeaf);
        vertex.setProperty(PropertyKeys.SOURCE_TYPE, leafType);
        vertex.setProperty(PropertyKeys.SOURCE_TEXT, text);
        return vertex;
    }
}
