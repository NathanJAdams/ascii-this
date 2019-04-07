package com.repocleaner.coreclean.languages.java.parser;

import com.repocleaner.parser_gen.ParserPart;
import com.repocleaner.parser_gen.ParserRule;

import static com.repocleaner.parser_gen.ParserPartBuilder.and;
import static com.repocleaner.parser_gen.ParserPartBuilder.ref;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesIdentifiers.Identifier;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesSeparators.Comma;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesSeparators.Dot;

public enum JavaParserRulesNames implements ParserRule {
    QualifiedIdentifierList {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "QualifiedIdentifierList",
                    ref(QualifiedIdentifier),
                    and(
                            "QualifiedIdentifierListExtra",
                            ref(Comma),
                            ref(QualifiedIdentifier)).any());
        }
    },
    QualifiedIdentifier {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "QualifiedIdentifier",
                    ref(Identifier),
                    ref(DotIdentifier).any());
        }
    },
    DotIdentifier {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "DotIdentifier",
                    ref(Dot),
                    ref(Identifier));
        }
    }
}
