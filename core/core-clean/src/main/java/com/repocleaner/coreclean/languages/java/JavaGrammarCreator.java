package com.repocleaner.coreclean.languages.java;

import com.repocleaner.coreclean.languages.java.lexer.*;
import com.repocleaner.coreclean.languages.java.parser.*;
import com.repocleaner.parser_gen.GrammarCreator;
import com.repocleaner.parser_gen.LexerRule;
import com.repocleaner.parser_gen.ParserRule;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JavaGrammarCreator implements GrammarCreator {
    @Override
    public List<ParserRule> createParserRules() {
        List<ParserRule> parserRules = new ArrayList<>();
        Arrays.stream(JavaParserRulesAnnotations.values()).forEach(parserRules::add);
        Arrays.stream(JavaParserRulesAnnotationTypes.values()).forEach(parserRules::add);
        Arrays.stream(JavaParserRulesEnums.values()).forEach(parserRules::add);
        Arrays.stream(JavaParserRulesExpressions.values()).forEach(parserRules::add);
        Arrays.stream(JavaParserRulesFields.values()).forEach(parserRules::add);
        Arrays.stream(JavaParserRulesFor.values()).forEach(parserRules::add);
        Arrays.stream(JavaParserRulesModifiers.values()).forEach(parserRules::add);
        Arrays.stream(JavaParserRulesNames.values()).forEach(parserRules::add);
        Arrays.stream(JavaParserRulesNormalClasses.values()).forEach(parserRules::add);
        Arrays.stream(JavaParserRulesNormalInterfaces.values()).forEach(parserRules::add);
        Arrays.stream(JavaParserRulesPackages.values()).forEach(parserRules::add);
        Arrays.stream(JavaParserRulesParameters.values()).forEach(parserRules::add);
        Arrays.stream(JavaParserRulesStatements.values()).forEach(parserRules::add);
        Arrays.stream(JavaParserRulesSwitch.values()).forEach(parserRules::add);
        Arrays.stream(JavaParserRulesTypes.values()).forEach(parserRules::add);
        return parserRules;
    }

    @Override
    public List<LexerRule> createLexerRules() {
        List<LexerRule> lexerRules = new ArrayList<>();
        Arrays.stream(JavaLexerRulesPrimitiveTypes.values()).forEach(lexerRules::add);
        Arrays.stream(JavaLexerRulesKeywords.values()).forEach(lexerRules::add);
        Arrays.stream(JavaLexerRulesSeparators.values()).forEach(lexerRules::add);
        Arrays.stream(JavaLexerRulesOperators.values()).forEach(lexerRules::add);
        Arrays.stream(JavaLexerRulesNullLiterals.values()).forEach(lexerRules::add);
        Arrays.stream(JavaLexerRulesNonCodeSymbols.values()).forEach(lexerRules::add);
        Arrays.stream(JavaLexerRulesIntegerLiterals.values()).forEach(lexerRules::add);
        Arrays.stream(JavaLexerRulesFloatingPointLiterals.values()).forEach(lexerRules::add);
        Arrays.stream(JavaLexerRulesBooleanLiterals.values()).forEach(lexerRules::add);
        Arrays.stream(JavaLexerRulesCharacterLiterals.values()).forEach(lexerRules::add);
        Arrays.stream(JavaLexerRulesStringLiterals.values()).forEach(lexerRules::add);
        Arrays.stream(JavaLexerRulesEscapeLiterals.values()).forEach(lexerRules::add);
        Arrays.stream(JavaLexerRulesIdentifiers.values()).forEach(lexerRules::add);
        Arrays.stream(JavaLexerRulesWhitespace.values()).forEach(lexerRules::add);
        return lexerRules;
    }
}
