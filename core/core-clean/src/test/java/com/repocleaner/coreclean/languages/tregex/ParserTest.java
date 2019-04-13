package com.repocleaner.coreclean.languages.tregex;

import com.repocleaner.coreclean.languages.java.JavaGrammarCreator;
import com.repocleaner.coreclean.languages.java.parser.JavaParserRulesPackages;
import com.repocleaner.parser_gen.Grammar;
import com.repocleaner.parser_gen.ParserException;
import com.repocleaner.parser_gen.streams.CharStream;
import com.repocleaner.parser_gen.streams.CharStreamReader;
import com.repocleaner.parser_gen.streams.FileCharStream;
import java.io.File;

public class ParserTest {
    public static void main(String[] args) throws ParserException {
        Grammar grammar = new JavaGrammarCreator().create();
        CharStream charStream = new FileCharStream(new File("C:/Users/Nathan/IdeaProjects/repoCleaner/core/core-clean/src/main/java/com/repocleaner/coreclean/tregex/TestFile.java"));
        CharStreamReader reader = new CharStreamReader(charStream);
        TreeNode node = grammar.parse(reader, JavaParserRulesPackages.CompilationUnit, new TreeNodeParseTreeFactory());
        System.out.println(node.pennString());
    }
}
