package com.repocleaner.coreclean.languages.java.lexer;

import com.repocleaner.parser_gen.LexerPart;
import com.repocleaner.parser_gen.LexerRule;
import com.repocleaner.parser_gen.ParseType;

import static com.repocleaner.parser_gen.LexerPartBuilder.and;
import static com.repocleaner.parser_gen.LexerPartBuilder.classes;
import static com.repocleaner.parser_gen.LexerPartBuilder.group;
import static com.repocleaner.parser_gen.LexerPartBuilder.literal;
import static com.repocleaner.parser_gen.LexerPartBuilder.or;
import static com.repocleaner.parser_gen.LexerPartBuilder.range;
import static com.repocleaner.parser_gen.LexerPartBuilder.ref;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesIntegerLiterals.HexDigit;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesIntegerLiterals.OctalDigit;

public enum JavaLexerRulesEscapeLiterals implements LexerRule {
    ZeroToThree {
        @Override
        public LexerPart createLexerPart() {
            return classes(range('0', '3'));
        }
    },
    OctalEscape {
        @Override
        public LexerPart createLexerPart() {
            return and(
                    literal("\\\\"),
                    or(
                            ref(OctalDigit),
                            and(ref(OctalDigit), ref(OctalDigit)),
                            and(ref(ZeroToThree), ref(OctalDigit), ref(OctalDigit))));
        }
    },
    UnicodeEscape {
        @Override
        public LexerPart createLexerPart() {
            return and(literal("\\\\"), literal('u').many(), ref(HexDigit), ref(HexDigit), ref(HexDigit), ref(HexDigit));
        }
    },
    EscapeSequence {
        @Override
        public LexerPart createLexerPart() {
            return or(
                    and(literal("\\"), classes(group("btnfr\"'\\"))),
                    ref(OctalEscape),
                    ref(UnicodeEscape));
        }
    };

    @Override
    public ParseType getParseType() {
        return ParseType.Fragment;
    }
}
