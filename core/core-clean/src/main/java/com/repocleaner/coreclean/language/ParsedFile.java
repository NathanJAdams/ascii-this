package com.repocleaner.coreclean.language;

import com.repocleaner.coreclean.antlr.HiddenTokensListener;
import org.antlr.v4.runtime.tree.ParseTree;

public class ParsedFile {
    private final ParseTree parseTree;
    private final HiddenTokensListener hiddenTokensListener;

    public ParsedFile(ParseTree parseTree, HiddenTokensListener hiddenTokensListener) {
        this.parseTree = parseTree;
        this.hiddenTokensListener = hiddenTokensListener;
    }

    public ParseTree getParseTree() {
        return parseTree;
    }

    public HiddenTokensListener getHiddenTokensListener() {
        return hiddenTokensListener;
    }
}
