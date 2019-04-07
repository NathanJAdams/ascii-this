package com.repocleaner.coreclean.languages.java.lexer;

import com.repocleaner.parser_gen.LexerPart;
import com.repocleaner.parser_gen.LexerRule;

import static com.repocleaner.parser_gen.LexerPartBuilder.literal;
import static com.repocleaner.parser_gen.LexerPartBuilder.or;

public enum JavaLexerRulesPrimitiveTypes implements LexerRule {
    IntegralType {
        @Override
        public LexerPart createLexerPart() {
            return or(literal("byte"), literal("short"), literal("int"), literal("long"), literal("char"));
        }
    },
    FloatingPointType {
        @Override
        public LexerPart createLexerPart() {
            return or(literal("float"), literal("double"));
        }
    },
    BooleanType {
        @Override
        public LexerPart createLexerPart() {
            return literal("boolean");
        }
    }
}
