package com.repocleaner.coreclean.languages.java.parser;

import com.repocleaner.parser_gen.ParserPart;
import com.repocleaner.parser_gen.ParserRule;

import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesIdentifiers.Identifier;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesNonCodeSymbols.At;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesOperators.Assign;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesSeparators.Comma;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesSeparators.LBrace;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesSeparators.LParen;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesSeparators.RBrace;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesSeparators.RParen;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesExpressions.Expression1;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesNames.QualifiedIdentifier;
import static com.repocleaner.parser_gen.ParserPartBuilder.and;
import static com.repocleaner.parser_gen.ParserPartBuilder.or;
import static com.repocleaner.parser_gen.ParserPartBuilder.ref;

public enum JavaParserRulesAnnotations implements ParserRule {
    Annotation {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "Annotation",
                    ref(At),
                    ref(QualifiedIdentifier),
                    and(
                            "Annotation_Elements",
                            ref(LParen),
                            ref(AnnotationElement).optional(),
                            ref(RParen)).optional());
        }
    },
    AnnotationElement {
        @Override
        public ParserPart createParserPart() {
            return or(
                    "AnnotationElement",
                    ref(ElementValuePairs),
                    ref(ElementValue));
        }
    },
    ElementValuePairs {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "ElementValuePairs",
                    ref(ElementValuePair),
                    and(
                            "ElementValuePairs_Extra",
                            ref(Comma),
                            ref(ElementValuePair).any()));
        }
    },
    ElementValuePair {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "ElementValuePair",
                    ref(Identifier),
                    ref(Assign),
                    ref(ElementValue));
        }
    },
    ElementValue {
        @Override
        public ParserPart createParserPart() {
            return or(
                    "ElementValue",
                    ref(Annotation),
                    ref(Expression1),
                    ref(ElementValueArrayInitializer));
        }
    },
    ElementValueArrayInitializer {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "ElementValueArrayInitializer",
                    ref(LBrace),
                    ref(ElementValues).optional(),
                    ref(Comma).optional(),
                    ref(RBrace));
        }
    },
    ElementValues {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "ElementValues",
                    ref(ElementValue),
                    and(
                            "ElementValues_Extra",
                            ref(Comma),
                            ref(ElementValue).any()));
        }
    }
}
