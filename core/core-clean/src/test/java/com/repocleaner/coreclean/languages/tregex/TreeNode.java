package com.repocleaner.coreclean.languages.tregex;

import edu.stanford.nlp.ling.Label;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeFactory;
import lombok.Setter;

@Setter
public class TreeNode

 extends Tree {
    private static final TreeNodeFactory TREE_FACTORY = new TreeNodeFactory();

    private Label label;
    private Tree parent = null;
    private Tree[] children = EMPTY_TREE_ARRAY;
    private String value;

    @Override
    public Tree parent() {
        return parent;
    }

    @Override
    public Tree[] children() {
        return children;
    }

    @Override
    public void setChildren(Tree[] children) {
        this.children = (children == null)
                ? EMPTY_TREE_ARRAY
                : children;
    }

    @Override
    public Label label() {
        return label;
    }

    @Override
    public String value() {
        return value;
    }

    @Override
    public TreeFactory treeFactory() {
        return TREE_FACTORY;
    }
}
