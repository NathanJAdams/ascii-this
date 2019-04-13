package com.repocleaner.coreclean.languages.tregex;

import edu.stanford.nlp.ling.Label;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeFactory;
import java.util.List;

public class TreeNodeFactory
        implements TreeFactory {
    @Override
    public Tree newLeaf(String s) {
        TreeNode treeNode = new TreeNode();
        treeNode.setValue(s);
        return treeNode;
    }

    @Override
    public Tree newLeaf(Label label) {
        TreeNode treeNode = new TreeNode();
        treeNode.setLabel(label);
        return treeNode;
    }

    public Tree newTreeNode(String parent, List<Tree> children) {
        TreeNode treeNode = new TreeNode();
        treeNode.setLabel(null);
        treeNode.setChildren(children);
        for (Tree child : children) {
            ((TreeNode) child).setParent(treeNode);
        }
        return treeNode;
    }

    public Tree newTreeNode(Label parentLabel, List<Tree> children) {
        TreeNode treeNode = new TreeNode();
        treeNode.setLabel(parentLabel);
        treeNode.setChildren(children);
        for (Tree child : children) {
            ((TreeNode) child).setParent(treeNode);
        }
        return treeNode;
    }
}
