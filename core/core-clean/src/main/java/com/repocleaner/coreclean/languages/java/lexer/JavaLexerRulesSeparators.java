package com.repocleaner.coreclean.languages.java.lexer;

import com.repocleaner.parser_gen.LexerPart;
import com.repocleaner.parser_gen.LexerRule;

import static com.repocleaner.parser_gen.LexerPartBuilder.literal;

public enum JavaLexerRulesSeparators implements LexerRule {
    LParen {
        @Override
        public LexerPart createLexerPart() {
            return literal("(");
        }
    },
    RParen {
        @Override
        public LexerPart createLexerPart() {
            return literal(")");
        }
    },
    LBrace {
        @Override
        public LexerPart createLexerPart() {
            return literal("{");
        }
    },
    RBrace {
        @Override
        public LexerPart createLexerPart() {
            return literal("}");
        }
    },
    LBrack {
        @Override
        public LexerPart createLexerPart() {
            return literal("[");
        }
    },
    RBrack {
        @Override
        public LexerPart createLexerPart() {
            return literal("]");
        }
    },
    Semi {
        @Override
        public LexerPart createLexerPart() {
            return literal(";");
        }
    },
    Comma {
        @Override
        public LexerPart createLexerPart() {
            return literal(",");
        }
    },
    Dot {
        @Override
        public LexerPart createLexerPart() {
            return literal(".");
        }
    }
}
