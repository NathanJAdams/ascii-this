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

public enum JavaLexerRulesIntegerLiterals implements LexerRule {
    IntegerTypeSuffix {
        @Override
        public LexerPart createLexerPart() {
            return classes(group("lL"));
        }
    },
    Digit {
        @Override
        public LexerPart createLexerPart() {
            return classes(range('0', '9'));
        }
    },
    NonZeroDigit {
        @Override
        public LexerPart createLexerPart() {
            return classes(range('1', '9'));
        }
    },
    DigitOrUnderscore {
        @Override
        public LexerPart createLexerPart() {
            return or(ref(Digit), literal('_'));
        }
    },
    DecimalNumeral {
        @Override
        public LexerPart createLexerPart() {
            return or(
                    literal('0'),
                    and(
                            ref(NonZeroDigit),
                            and(
                                    ref(DigitOrUnderscore).any(),
                                    ref(Digit)).optional()));
        }
    },
    DecimalIntegerLiteral {
        @Override
        public LexerPart createLexerPart() {
            return and(ref(DecimalNumeral), ref(IntegerTypeSuffix).optional());
        }
    },
    Digits {
        @Override
        public LexerPart createLexerPart() {
            return and(
                    ref(Digit),
                    and(
                            ref(DigitOrUnderscore).any(),
                            ref(Digit)
                    ).optional());
        }
    },
    Underscores {
        @Override
        public LexerPart createLexerPart() {
            return literal('_').many();
        }
    },
    HexDigit {
        @Override
        public LexerPart createLexerPart() {
            return classes(range('0', '9'), range('a', 'f'), range('A', 'F'));
        }
    },
    HexDigitOrUnderscore {
        @Override
        public LexerPart createLexerPart() {
            return or(ref(HexDigit), literal('_'));
        }
    },
    HexDigits {
        @Override
        public LexerPart createLexerPart() {
            return and(
                    ref(HexDigit),
                    and(
                            ref(HexDigitOrUnderscore).any(),
                            ref(HexDigit)
                    ).optional());
        }
    },
    HexNumeral {
        @Override
        public LexerPart createLexerPart() {
            return and(literal('0'), classes(group("xX")), ref(HexDigits));
        }
    },
    HexIntegerLiteral {
        @Override
        public LexerPart createLexerPart() {
            return and(ref(HexNumeral), ref(IntegerTypeSuffix).optional());
        }
    },
    OctalDigit {
        @Override
        public LexerPart createLexerPart() {
            return classes(range('0', '7'));
        }
    },
    OctalDigitOrUnderscore {
        @Override
        public LexerPart createLexerPart() {
            return or(ref(OctalDigit), literal('_'));
        }
    },
    OctalDigits {
        @Override
        public LexerPart createLexerPart() {
            return and(
                    ref(OctalDigit),
                    and(
                            ref(OctalDigitOrUnderscore).any(),
                            ref(OctalDigit)
                    ).optional());
        }
    },
    OctalNumeral {
        @Override
        public LexerPart createLexerPart() {
            return or(literal('0'), ref(Underscores).optional(), ref(OctalDigits));
        }
    },
    OctalIntegerLiteral {
        @Override
        public LexerPart createLexerPart() {
            return and(ref(OctalNumeral), ref(IntegerTypeSuffix).optional());
        }
    },
    BinaryDigit {
        @Override
        public LexerPart createLexerPart() {
            return classes(group("01"));
        }
    },
    BinaryDigitOrUnderscore {
        @Override
        public LexerPart createLexerPart() {
            return or(ref(BinaryDigit), literal('_'));
        }
    },
    BinaryDigits {
        @Override
        public LexerPart createLexerPart() {
            return and(
                    ref(BinaryDigit),
                    and(
                            ref(BinaryDigitOrUnderscore).any(),
                            ref(BinaryDigit)
                    ).optional());
        }
    },
    BinaryNumeral {
        @Override
        public LexerPart createLexerPart() {
            return and(literal('0'), classes(group("bB")), ref(BinaryDigits));
        }
    },
    BinaryIntegerLiteral {
        @Override
        public LexerPart createLexerPart() {
            return and(ref(BinaryNumeral), ref(IntegerTypeSuffix).optional());
        }
    },
    IntegerLiteral {
        @Override
        public ParseType getParseType() {
            return ParseType.Included;
        }

        @Override
        public LexerPart createLexerPart() {
            return or(ref(DecimalIntegerLiteral), ref(HexIntegerLiteral), ref(OctalIntegerLiteral), ref(BinaryIntegerLiteral));
        }
    };

    @Override
    public ParseType getParseType() {
        return ParseType.Fragment;
    }
}
