package com.repocleaner.coreclean.tregex;

import com.repocleaner.coreclean.languages.java.JavaGrammarCreator;
import com.repocleaner.parser_gen.Grammar;
import com.repocleaner.parser_gen.ParseTreeFactory;
import com.repocleaner.coreclean.languages.java.parser.JavaParserRulesPackages;
import com.repocleaner.parser_gen.streams.CharStream;
import com.repocleaner.parser_gen.streams.CharStreamReader;
import com.repocleaner.parser_gen.streams.FileCharStream;
import java.io.File;

public class ExciseTest {
//    private static void test() {
//        Tree tree = Tree.valueOf("(ROOT (S (T t) (U u) (V v)))");
//        System.out.println(tree.pennString());
//        TregexPattern regex = TregexPattern.compile("T=a");
//        TsurgeonPattern operation = Tsurgeon.parseOperation("[excise a a]");
//        Tsurgeon.processPattern(regex, operation, tree);
//        String pennString = tree.pennString();
//        System.out.println(pennString);
//
//        tree = Tree.valueOf("(ROOT (S (T t) (U u) (V v)))");
//        regex = TregexPattern.compile("T=a");
//        operation = Tsurgeon.parseOperation("relabel a SSS");
//        Tsurgeon.processPattern(regex, operation, tree);
//        pennString = tree.pennString();
//        System.out.println(pennString);
//
//        tree = Tree.valueOf("(ROOT (S (T t) (U u) (V v)))");
//        regex = TregexPattern.compile("T=a");
//        operation = Tsurgeon.parseOperation("delete a");
//        Tsurgeon.processPattern(regex, operation, tree);
//        pennString = tree.pennString();
//        System.out.println(pennString);
//    }
//
//    @Test
//    public void testSimple() {
//        Grammar grammar = new JavaGrammarCreator().create();
//        grammar.compile();
//        ParseTreeFactory<TreeNode, TreeNode, TreeNode> parseTreeFactory = new TreeNodeParseTreeFactory();
//        CharStream charStream = new FileCharStream(new File("C:/Users/Nathan/IdeaProjects/JavaParser/src/main/resources/Test.txt"));
//        CharStreamReader reader = new CharStreamReader(charStream);
//        Tree tree = null;
//        try {
//            tree = grammar.parse(reader, JavaParserRulesPackages.CompilationUnit, parseTreeFactory);
//        } catch (Throwable e) {
//            do {
//                System.out.println(e.getMessage());
//                for (Throwable suppressed : e.getSuppressed()) {
//                    System.out.println(suppressed.getMessage());
//                }
//                Throwable next = e.getCause();
//                e = (next == e)
//                        ? null
//                        : next;
//            } while (e != null);
//        }
//        System.out.println(tree.pennString());
//        TregexPattern regex = TregexPattern.compile("ClassBodyDeclaration_Member=a");
//        TregexMatcher matcher = regex.matcher(tree);
//        int count = 0;
//        while (matcher.findNextMatchingNode()) {
//            if (count > 0) {
//                Tree match = matcher.getMatch();
//                int index = match.parent().objectIndexOf(match);
//                System.out.println(index);
//                match.parent().removeChild(index);
//            }
//            count++;
//        }
//        System.out.println(tree.pennString());
//    }
}
