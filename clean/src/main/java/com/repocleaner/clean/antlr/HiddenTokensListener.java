package com.repocleaner.clean.antlr;

import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HiddenTokensListener implements ParseTreeListener {
    private final BufferedTokenStream bufferedTokenStream;
    private final boolean isHiddenTextQuoted;
    private final Map<TerminalNode, String> terminalHiddenText = new HashMap<>();

    public HiddenTokensListener(BufferedTokenStream bufferedTokenStream, boolean isHiddenTextQuoted) {
        this.bufferedTokenStream = bufferedTokenStream;
        this.isHiddenTextQuoted = isHiddenTextQuoted;
    }

    public String getTerminalPreviousHiddenText(TerminalNode terminalNode) {
        return terminalHiddenText.get(terminalNode);
    }

    @Override
    public void visitTerminal(TerminalNode terminalNode) {
        int tokenIndex = terminalNode.getSymbol().getTokenIndex();
        List<Token> hiddenTokens = bufferedTokenStream.getHiddenTokensToLeft(tokenIndex);
        String hiddenText = toHiddenText(hiddenTokens);
        if (hiddenText != null) {
            terminalHiddenText.put(terminalNode, hiddenText);
        }
    }

    @Override
    public void visitErrorNode(ErrorNode errorNode) {
    }

    @Override
    public void enterEveryRule(ParserRuleContext parserRuleContext) {
    }

    @Override
    public void exitEveryRule(ParserRuleContext parserRuleContext) {
    }

    private String toHiddenText(List<Token> hiddenTokens) {
        if (hiddenTokens == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        if (isHiddenTextQuoted) {
            sb.append('\'');
        }
        hiddenTokens.stream()
                .map(Token::getText)
                .forEach(sb::append);
        if (isHiddenTextQuoted) {
            sb.append('\'');
        }
        return sb.toString();
    }
}
