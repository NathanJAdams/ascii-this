package com.repocleaner.clean.language;

import org.antlr.v4.runtime.tree.ParseTree;
import com.repocleaner.clean.antlr.HiddenTokensListener;

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
