package com.repocleaner.coreclean.languages.java.parser;

import com.repocleaner.parser_gen.ParserPart;
import com.repocleaner.parser_gen.ParserRule;

import static com.repocleaner.parser_gen.ParserPartBuilder.and;
import static com.repocleaner.parser_gen.ParserPartBuilder.or;
import static com.repocleaner.parser_gen.ParserPartBuilder.ref;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesIdentifiers.Identifier;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesOperators.Assign;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesSeparators.Comma;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesSeparators.LBrace;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesSeparators.RBrace;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesExpressions.Brackets;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesExpressions.Expression;

public enum JavaParserRulesFields implements ParserRule {
    VariableDeclaratorId {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "VariableDeclaratorId",
                    ref(Identifier),
                    ref(Brackets).any());
        }
    },
    VariableDeclarators {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "VariableDeclarators",
                    ref(VariableDeclarator),
                    and(
                            "VariableDeclarators_Extra",
                            ref(Comma),
                            ref(VariableDeclarator)).any());
        }
    },
    VariableDeclarator {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "VariableDeclarator",
                    ref(Identifier),
                    ref(VariableDeclaratorRest));
        }
    },
    VariableDeclaratorRest {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "VariableDeclaratorRest",
                    ref(Brackets).any(),
                    and(
                            "VariableDeclaratorRest_Assign",
                            ref(Assign),
                            ref(VariableInitializer)).optional());
        }
    },
    VariableInitializer {
        @Override
        public ParserPart createParserPart() {
            return or(
                    "VariableInitializer",
                    ref(Expression),
                    ref(ArrayInitializer));
        }
    },
    ArrayInitializer {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "ArrayInitializer",
                    ref(LBrace),
                    and(
                            "ArrayInitializer_Expressions",
                            ref(VariableInitializer),
                            and(
                                    "ArrayInitializer_Expressions_Extra",
                                    ref(Comma),
                                    ref(VariableInitializer)).any(),
                            ref(Comma).optional()).optional(),
                    ref(RBrace));
        }
    }
}
