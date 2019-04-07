package com.repocleaner.coreclean.languages.java.lexer;

import com.repocleaner.parser_gen.LexerPart;
import com.repocleaner.parser_gen.LexerRule;
import com.repocleaner.parser_gen.ParseType;

import static com.repocleaner.parser_gen.LexerPartBuilder.and;
import static com.repocleaner.parser_gen.LexerPartBuilder.classes;
import static com.repocleaner.parser_gen.LexerPartBuilder.group;
import static com.repocleaner.parser_gen.LexerPartBuilder.literal;
import static com.repocleaner.parser_gen.LexerPartBuilder.or;
import static com.repocleaner.parser_gen.LexerPartBuilder.ref;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesEscapeLiterals.EscapeSequence;

public enum JavaLexerRulesCharacterLiterals implements LexerRule {
    SingleCharacter {
        @Override
        public ParseType getParseType() {
            return ParseType.Fragment;
        }

        @Override
        public LexerPart createLexerPart() {
            return classes(true, group("'\\\r\n"));
        }
    },
    CharacterLiteral {
        @Override
        public LexerPart createLexerPart() {
            return or(
                    and(literal('\''), ref(SingleCharacter), literal('\'')),
                    and(literal('\''), ref(EscapeSequence), literal('\'')));
        }
    }
}
