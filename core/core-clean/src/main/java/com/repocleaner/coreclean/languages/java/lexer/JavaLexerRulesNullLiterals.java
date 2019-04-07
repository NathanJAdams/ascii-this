package com.repocleaner.coreclean.languages.java.lexer;

import com.repocleaner.parser_gen.LexerPart;
import com.repocleaner.parser_gen.LexerRule;

import static com.repocleaner.parser_gen.LexerPartBuilder.literal;

public enum JavaLexerRulesNullLiterals implements LexerRule {
    NullLiteral {
        @Override
        public LexerPart createLexerPart() {
            return literal("null");
        }
    }
}
