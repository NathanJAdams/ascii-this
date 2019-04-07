package com.repocleaner.coreclean.languages.java.lexer;

import com.repocleaner.parser_gen.LexerPart;
import com.repocleaner.parser_gen.LexerRule;
import com.repocleaner.parser_gen.ParseType;

import static com.repocleaner.parser_gen.LexerPartBuilder.and;
import static com.repocleaner.parser_gen.LexerPartBuilder.anyChar;
import static com.repocleaner.parser_gen.LexerPartBuilder.classes;
import static com.repocleaner.parser_gen.LexerPartBuilder.group;
import static com.repocleaner.parser_gen.LexerPartBuilder.literal;

public enum JavaLexerRulesWhitespace implements LexerRule {
    Whitespace {
        @Override
        public LexerPart createLexerPart() {
            return classes(group(" \t\r\n\u000C")).many();
        }
    },
    MultiLineComment {
        @Override
        public LexerPart createLexerPart() {
            return and(literal("/*"), classes(anyChar()).any().optional(), literal("*/"));
        }
    },
    SingleLineComment {
        @Override
        public LexerPart createLexerPart() {
            return and(literal("//"), classes(true, group("\r\n")).any());
        }
    };

    @Override
    public ParseType getParseType() {
        return ParseType.Hidden;
    }
}
