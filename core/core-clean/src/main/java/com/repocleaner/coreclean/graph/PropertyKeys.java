package com.repocleaner.coreclean.graph;

public class PropertyKeys {
    public static final PropertyKey<GraphElementType> TYPE = new PropertyKey<>("TYPE");

    public static final PropertyKey<String> FOLDER_NAME = new PropertyKey<>("FOLDER_NAME");

    public static final PropertyKey<String> FILE_NAME = new PropertyKey<>("FILE_NAME");
    public static final PropertyKey<Boolean> FILE_HAS_CHANGED = new PropertyKey<>("FILE_HAS_CHANGED");

    public static final PropertyKey<Integer> SOURCE_TYPE = new PropertyKey<>("SOURCE_TYPE");
    public static final PropertyKey<String> SOURCE_TEXT = new PropertyKey<>("SOURCE_TEXT");

    public static final PropertyKey<String> HIDDEN_TEXT = new PropertyKey<>("HIDDEN_TEXT");
}
