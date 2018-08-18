package clean.language;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import clean.antlr.HiddenTokensListener;

import java.io.IOException;
import java.util.function.Function;

public class ParsedFileCreator {
    private final Function<CharStream, Lexer> lexerFunction;
    private final Function<TokenStream, Parser> parserFunction;
    private final Function<Parser, ParseTree> parseTreeFunction;

    @SuppressWarnings("unchecked")
    public <P extends Parser> ParsedFileCreator(Function<CharStream, Lexer> lexerFunction, Function<TokenStream, P> parserFunction, Function<P, ParseTree> parseTreeFunction) {
        this.lexerFunction = lexerFunction;
        this.parserFunction = (Function<TokenStream, Parser>) parserFunction;
        this.parseTreeFunction = (Function<Parser, ParseTree>) parseTreeFunction;
    }

    public ParsedFile parse(String filePath) {
        CharStream charStream;
        try {
            charStream = CharStreams.fromFileName(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        Lexer lexer = lexerFunction.apply(charStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        Parser parser = parserFunction.apply(tokenStream);
        HiddenTokensListener hiddenTokensListener = new HiddenTokensListener(tokenStream, false);
        parser.addParseListener(hiddenTokensListener);
        ParseTree parseTree = parseTreeFunction.apply(parser);
        return new ParsedFile(parseTree, hiddenTokensListener);
    }
}
