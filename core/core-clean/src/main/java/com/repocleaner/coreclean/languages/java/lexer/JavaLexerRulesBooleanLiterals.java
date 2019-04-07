package com.repocleaner.coreclean.languages.java.lexer;

import com.repocleaner.parser_gen.LexerPart;
import com.repocleaner.parser_gen.LexerRule;

import static com.repocleaner.parser_gen.LexerPartBuilder.literal;
import static com.repocleaner.parser_gen.LexerPartBuilder.or;

public enum JavaLexerRulesBooleanLiterals implements LexerRule {
    BooleanLiteral {
        @Override
        public LexerPart createLexerPart() {
            return or(literal("true"), literal("false"));
        }
    }
}
