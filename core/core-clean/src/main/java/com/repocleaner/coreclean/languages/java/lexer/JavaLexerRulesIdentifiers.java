package com.repocleaner.coreclean.languages.java.lexer;

import com.repocleaner.parser_gen.LexerPart;
import com.repocleaner.parser_gen.LexerRule;
import com.repocleaner.parser_gen.ParseType;

import static com.repocleaner.parser_gen.LexerPartBuilder.and;
import static com.repocleaner.parser_gen.LexerPartBuilder.classes;
import static com.repocleaner.parser_gen.LexerPartBuilder.group;
import static com.repocleaner.parser_gen.LexerPartBuilder.or;
import static com.repocleaner.parser_gen.LexerPartBuilder.range;
import static com.repocleaner.parser_gen.LexerPartBuilder.ref;

public enum JavaLexerRulesIdentifiers implements LexerRule {
    Letter {
        @Override
        public ParseType getParseType() {
            return ParseType.Fragment;
        }

        @Override
        public LexerPart createLexerPart() {
            return or(
                    classes(range('a', 'z'), range('A', 'Z'), group("$_"))
//                    ,
//                    classes(true, range('\u0000', '\u007F'), range('\uD800', '\uDBFF')),
//                    and(
//                            classes(range('\uD800', '\uDBFF')),
//                            classes(range('\uDC00', '\uDFFF'))))
            );
        }
    },
    LetterOrDigit {
        @Override
        public ParseType getParseType() {
            return ParseType.Fragment;
        }

        @Override
        public LexerPart createLexerPart() {
            return or(ref(Letter), classes(range('0', '9')));
        }
    },
    Identifier {
        @Override
        public LexerPart createLexerPart() {
            return and(ref(Letter), ref(LetterOrDigit).any());
        }
    }
}
