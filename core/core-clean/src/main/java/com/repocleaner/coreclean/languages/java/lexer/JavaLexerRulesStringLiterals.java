package com.repocleaner.coreclean.languages.java.lexer;

import com.repocleaner.parser_gen.LexerPart;
import com.repocleaner.parser_gen.LexerRule;

import static com.repocleaner.parser_gen.LexerPartBuilder.and;
import static com.repocleaner.parser_gen.LexerPartBuilder.classes;
import static com.repocleaner.parser_gen.LexerPartBuilder.group;
import static com.repocleaner.parser_gen.LexerPartBuilder.literal;
import static com.repocleaner.parser_gen.LexerPartBuilder.or;
import static com.repocleaner.parser_gen.LexerPartBuilder.ref;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesEscapeLiterals.EscapeSequence;

public enum JavaLexerRulesStringLiterals implements LexerRule {
    StringCharacter {
        @Override
        public LexerPart createLexerPart() {
            return or(classes(true, group("\"\\\r\n")), ref(EscapeSequence));
        }
    },
    StringLiteral {
        @Override
        public LexerPart createLexerPart() {
            return and(literal('"'), ref(StringCharacter).any(), literal('"'));
        }
    }
}
