package com.repocleaner.coreclean.languages.tregex;

import com.repocleaner.parser_gen.ParseTreeFactory;
import com.repocleaner.parser_gen.ParsedToken;
import edu.stanford.nlp.ling.LabeledWord;
import java.util.List;

public class TreeNodeParseTreeFactory
        implements ParseTreeFactory<TreeNode, TreeNode, TreeNode> {
    @Override
    public TreeNode createLeaf(String name, ParsedToken parsedToken) {
        TreeNode leaf = new TreeNode();
        leaf.setLabel(new LabeledWord(parsedToken.getLexedToken().getToken()));
        leaf.setValue(parsedToken.getLexedToken().getToken());
        return leaf;
    }

    @Override
    public TreeNode createBranch(String name, List<TreeNode> children) {
        TreeNode branch = new TreeNode();
        branch.setLabel(new LabeledWord(name));
        branch.setChildren(children);
        for (TreeNode child : children) {
            child.setParent(branch);
        }
        return branch;
    }
}
