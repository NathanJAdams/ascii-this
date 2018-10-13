package com.repocleaner.coreclean.antlr;

import com.repocleaner.util.StreamUtil;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.Iterator;
import java.util.function.Function;
import java.util.stream.Stream;

public class ParseTreeUtil implements Function<ParseTree, Stream<ParseTree>> {
    public Stream<ParseTree> apply(ParseTree root) {
        return StreamUtil.stream(iterable(root));
    }

    public Iterable<ParseTree> iterable(ParseTree parseTree) {
        return () -> new Iterator<ParseTree>() {
            private ParseTree next = calculateNext(parseTree);

            @Override
            public boolean hasNext() {
                return (next != null);
            }

            @Override
            public ParseTree next() {
                ParseTree toReturn = next;
                next = calculateNext(toReturn);
                return next;
            }

            private ParseTree calculateNext(ParseTree previous) {
                if (previous.getChildCount() > 0) {
                    return previous.getChild(0);
                }
                ParseTree previousParent = previous.getParent();
                if (previousParent == null) {
                    return null;
                }
                int previousIndexInParent = indexInParent(previous);
                if (previousIndexInParent == -1) {
                    return null;
                }
                int previousSiblingIndex = previousIndexInParent + 1;
                if (previousParent.getChildCount() == previousSiblingIndex) {
                    return calculateNext(previousParent);
                }
                return previousParent.getChild(previousSiblingIndex);
            }

            private int indexInParent(ParseTree parseTree) {
                ParseTree parent = parseTree.getParent();
                int siblingCount = parent.getChildCount();
                for (int i = 0; i < siblingCount; i++) {
                    if (parent.getChild(i) == parseTree) {
                        return i;
                    }
                }
                return -1;
            }
        };
    }
}
