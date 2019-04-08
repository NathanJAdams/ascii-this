package com.repocleaner.coreclean.languages;

import com.repocleaner.coreclean.languages.java.JavaGrammarCreator;
import com.repocleaner.coreclean.languages.java.parser.JavaParserRulesExpressions;
import com.repocleaner.coreclean.languages.java.parser.JavaParserRulesFields;
import com.repocleaner.coreclean.languages.java.parser.JavaParserRulesNames;
import com.repocleaner.coreclean.languages.java.parser.JavaParserRulesParameters;
import com.repocleaner.coreclean.languages.java.parser.JavaParserRulesStatements;
import com.repocleaner.coreclean.languages.java.parser.JavaParserRulesTypes;
import com.repocleaner.parser_gen.Grammar;
import com.repocleaner.parser_gen.ImmutableParseTreeFactory;
import com.repocleaner.parser_gen.ImmutableTreeNode;
import com.repocleaner.parser_gen.ParserException;
import com.repocleaner.parser_gen.ParserRule;
import com.repocleaner.parser_gen.streams.CharStream;
import com.repocleaner.parser_gen.streams.CharStreamReader;
import com.repocleaner.parser_gen.streams.StringCharStream;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ParseJavaGrammarTest {
    @Test
    public void parseIdentifiers() throws ParserException {
        testText(".xyz", JavaParserRulesNames.DotIdentifier);
        testText("abc.jkl", JavaParserRulesNames.QualifiedIdentifier);
        testText("abc.jkl.xyz", JavaParserRulesNames.QualifiedIdentifier);
    }

    @Test
    public void parseBasicTypes() throws ParserException {
        testText("boolean", JavaParserRulesTypes.BasicType, "BooleanType");
        testText("byte", JavaParserRulesTypes.BasicType, "IntegralType");
        testText("short", JavaParserRulesTypes.BasicType, "IntegralType");
        testText("int", JavaParserRulesTypes.BasicType, "IntegralType");
        testText("long", JavaParserRulesTypes.BasicType, "IntegralType");
        testText("char", JavaParserRulesTypes.BasicType, "IntegralType");
        testText("float", JavaParserRulesTypes.BasicType, "FloatingPointType");
        testText("double", JavaParserRulesTypes.BasicType, "FloatingPointType");
    }

    @Test
    public void parseReferenceTypes() throws ParserException {
        testText("NormalType", JavaParserRulesTypes.ReferenceType);
        testText("GenericType<T>", JavaParserRulesTypes.ReferenceType);
        testText("GenericInnerType.Inner<T>", JavaParserRulesTypes.ReferenceType);
        testText("InnerType.Inner", JavaParserRulesTypes.ReferenceType);
        testText("GenericInnerType<T>.Inner<T>", JavaParserRulesTypes.ReferenceType);
    }

    @Test
    public void parseParameters() throws ParserException {
        testText("()", JavaParserRulesParameters.FormalParameters);
        testText("(int a)", JavaParserRulesParameters.FormalParameters);
        testText("(String a, int b)", JavaParserRulesParameters.FormalParameters);
        testText("(int[] a)", JavaParserRulesParameters.FormalParameters);
        testText("(String[] a, int[] b)", JavaParserRulesParameters.FormalParameters);
        testText("(Integer... a)", JavaParserRulesParameters.FormalParameters);
        testText("(String a, Integer... b)", JavaParserRulesParameters.FormalParameters);
        testText("(String[] a, Integer... b)", JavaParserRulesParameters.FormalParameters);
    }

    @Test
    public void parseExpressions() throws ParserException {
        testText("a", JavaParserRulesExpressions.Expression);
        testText("0", JavaParserRulesExpressions.Expression);
        testText("123", JavaParserRulesExpressions.Expression);
        testText("123.456", JavaParserRulesExpressions.Expression);
        testText("21.54E-12", JavaParserRulesExpressions.Expression);
        testText("-789.321", JavaParserRulesExpressions.Expression);
        testText("a[0]", JavaParserRulesExpressions.Expression);
        testText("a=0", JavaParserRulesExpressions.Expression);
        testText("a==b", JavaParserRulesExpressions.Expression);
        testText("a<<0", JavaParserRulesExpressions.Expression);
        testText("a>9", JavaParserRulesExpressions.Expression);
        testText("(a)", JavaParserRulesExpressions.Expression);
        testText("System.nanoTime();", JavaParserRulesExpressions.Expression);
    }

    @Test
    public void parseStatements() throws ParserException {
        testText("lbl: if (a) a++;", JavaParserRulesStatements.Statement, "Statement_Identifier");

        testText("if (a) a++;", JavaParserRulesStatements.Statement, "Statement_If");
        testText("if (a) {a++;}", JavaParserRulesStatements.Statement, "Statement_If");
        testText("if (a) {a++;} else a--;", JavaParserRulesStatements.Statement, "Statement_If");
        testText("if (a) {a++;} else {a--;}", JavaParserRulesStatements.Statement, "Statement_If");

        testText("--a;", JavaParserRulesStatements.Statement, "Statement_Expression");
        testText("a++;", JavaParserRulesStatements.Statement, "Statement_Expression");
        testText("a+b;", JavaParserRulesStatements.Statement, "Statement_Expression");
        testText("10*8;", JavaParserRulesStatements.Statement, "Statement_Expression");
        testText("long preCompile = System.nanoTime();", JavaParserRulesStatements.LocalVariableDeclarationStatement);
        testText("String a = \"string\";", JavaParserRulesStatements.LocalVariableDeclarationStatement);
        testText("boolean b = true;", JavaParserRulesStatements.LocalVariableDeclarationStatement);
        testText("int a =1;", JavaParserRulesStatements.LocalVariableDeclarationStatement);

        testText("assert true;", JavaParserRulesStatements.Statement, "Statement_Assert");
        testText("assert true : false;", JavaParserRulesStatements.Statement, "Statement_Assert");

        testText("switch (a) { case 1:}", JavaParserRulesStatements.Statement, "Statement_Switch");
        testText("switch (a) { case 1:a++;}", JavaParserRulesStatements.Statement, "Statement_Switch");
        testText("switch (a) { case 1:case 2:a++;}", JavaParserRulesStatements.Statement, "Statement_Switch");
    }

    @Test
    public void test() throws ParserException {
        int a = 10;
        AtomicBoolean ab = new AtomicBoolean();
        testText("10", JavaParserRulesExpressions.Expression);
        testText("(a / 10 * 2);", JavaParserRulesExpressions.Expression);
        testText("a = (10)", JavaParserRulesFields.VariableDeclarator);

        testText("int", JavaParserRulesTypes.Type);
        testText("a = (10)", JavaParserRulesFields.VariableDeclarators);

        testText("int a = (10);", JavaParserRulesStatements.LocalVariableDeclarationStatement);
        testText("System.out.println(ab.getAndSet(a == (a / 10)));", JavaParserRulesStatements.Statement, "Statement_Expression");
        testText("System.out.println(ab.getAndSet(a == (a / 10 * 2)));", JavaParserRulesStatements.Statement, "Statement_Expression");
    }

    private void testText(String text, ParserRule parserRule) throws ParserException {
        testText(text, parserRule, parserRule.name());
    }

    private void testText(String text, ParserRule parserRule, String name) throws ParserException {
        CharStream charStream = new StringCharStream(text);
        CharStreamReader reader = new CharStreamReader(charStream);
        Grammar grammar = new JavaGrammarCreator().create();
        ImmutableParseTreeFactory parseTreeFactory = new ImmutableParseTreeFactory();
        ImmutableTreeNode node = grammar.parse(reader, parserRule, parseTreeFactory);
        if (node == null) {
            Assert.fail();
        } else {
            assertEquals(name, node.getName());
        }
    }
}
