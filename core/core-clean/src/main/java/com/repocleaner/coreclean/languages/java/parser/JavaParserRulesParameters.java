package com.repocleaner.coreclean.languages.java.parser;

import com.repocleaner.parser_gen.ParserPart;
import com.repocleaner.parser_gen.ParserRule;

import static com.repocleaner.parser_gen.ParserPartBuilder.and;
import static com.repocleaner.parser_gen.ParserPartBuilder.or;
import static com.repocleaner.parser_gen.ParserPartBuilder.ref;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesKeywords.Final;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesNonCodeSymbols.Ellipsis;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesSeparators.Comma;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesSeparators.LParen;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesSeparators.RParen;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesAnnotations.Annotation;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesFields.VariableDeclaratorId;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesTypes.Type;

public enum JavaParserRulesParameters implements ParserRule {
    FormalParameters {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "FormalParameters",
                    ref(LParen),
                    ref(FormalParameterDeclarations).optional(),
                    ref(RParen));
        }
    },
    FormalParameterDeclarations {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "FormalParameterDeclarations",
                    and(
                            "FormalParameterDeclarations_First",
                            ref(FormalParameterDeclaration),
                            ref(Comma)).any(),
                    or(
                            "FormalParameterDeclarations_Last",
                            ref(FormalParameterDeclaration),
                            ref(VarargsFormalParameterDeclaration)));
        }
    },
    FormalParameterDeclaration {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "FormalParameterDeclaration",
                    ref(VariableModifier).any(),
                    ref(Type),
                    ref(VariableDeclaratorId));
        }
    },
    VarargsFormalParameterDeclaration {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "FormalParameterDeclaration",
                    ref(VariableModifier).any(),
                    ref(Type),
                    ref(Ellipsis),
                    ref(VariableDeclaratorId));
        }
    },
    VariableModifier {
        @Override
        public ParserPart createParserPart() {
            return or(
                    "VariableModifier",
                    ref(Final),
                    ref(Annotation));
        }
    }
}
