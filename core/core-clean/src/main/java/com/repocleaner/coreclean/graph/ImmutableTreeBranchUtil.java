package com.repocleaner.coreclean.graph;

import com.repocleaner.parser_gen.ImmutableTreeBranch;
import com.repocleaner.parser_gen.ImmutableTreeNode;
import com.repocleaner.util.StreamUtil;
import java.util.Iterator;
import java.util.function.Function;
import java.util.stream.Stream;

public class ImmutableTreeBranchUtil implements Function<ImmutableTreeNode, Stream<ImmutableTreeNode>> {
    public Stream<ImmutableTreeNode> apply(ImmutableTreeNode root) {
        return StreamUtil.stream(iterable(root));
    }

    public Iterable<ImmutableTreeNode> iterable(ImmutableTreeNode node) {
        return () -> new Iterator<ImmutableTreeNode>() {
            private ImmutableTreeNode next = calculateNext(node);

            @Override
            public boolean hasNext() {
                return (next != null);
            }

            @Override
            public ImmutableTreeNode next() {
                ImmutableTreeNode toReturn = next;
                next = calculateNext(toReturn);
                return next;
            }

            private ImmutableTreeNode calculateNext(ImmutableTreeNode previous) {
                if(previous instanceof ImmutableTreeBranch){
                    ImmutableTreeBranch branch=(ImmutableTreeBranch)node;
                    if(!branch.getChildren().isEmpty()){
                        return branch.getChildren().get(0);
                    }
                }
//               ImmutableTreeNode previousParent = previous.getParent();
//                if (previousParent == null) {
//                    return null;
//                }
//                int previousIndexInParent = indexInParent(previous);
//                if (previousIndexInParent == -1) {
//                    return null;
//                }
//                int previousSiblingIndex = previousIndexInParent + 1;
//                if (previousParent.getChildCount() == previousSiblingIndex) {
//                    return calculateNext(previousParent);
//                }
//                return previousParent.getChild(previousSiblingIndex);
                return null;
            }
//
//            private int indexInParent(ImmutableTreeNode node) {
//                ImmutableTreeNode parent = node.getParent();
//                int siblingCount = parent.getChildCount();
//                for (int i = 0; i < siblingCount; i++) {
//                    if (parent.getChild(i) == node) {
//                        return i;
//                    }
//                }
//                return -1;
//            }
        };
    }
}
