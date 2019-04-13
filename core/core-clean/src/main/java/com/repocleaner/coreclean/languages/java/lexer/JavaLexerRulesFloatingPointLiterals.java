package com.repocleaner.coreclean.languages.java.lexer;

import com.repocleaner.parser_gen.LexerPart;
import com.repocleaner.parser_gen.LexerRule;
import com.repocleaner.parser_gen.ParseType;

import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesIntegerLiterals.Digits;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesIntegerLiterals.HexDigits;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesIntegerLiterals.HexNumeral;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesSeparators.Dot;
import static com.repocleaner.parser_gen.LexerPartBuilder.and;
import static com.repocleaner.parser_gen.LexerPartBuilder.classes;
import static com.repocleaner.parser_gen.LexerPartBuilder.group;
import static com.repocleaner.parser_gen.LexerPartBuilder.literal;
import static com.repocleaner.parser_gen.LexerPartBuilder.or;
import static com.repocleaner.parser_gen.LexerPartBuilder.ref;

public enum JavaLexerRulesFloatingPointLiterals implements LexerRule {
    BinaryExponentIndicator {
        @Override
        public LexerPart createLexerPart() {
            return classes(group("pP"));
        }
    },
    HexSignificand {
        @Override
        public LexerPart createLexerPart() {
            return or(
                    and(ref(HexNumeral), literal('.').optional()),
                    and(literal('0'), classes(group("xX")).optional(), literal('.'), ref(HexDigits)));
        }
    },
    FloatTypeSuffix {
        @Override
        public LexerPart createLexerPart() {
            return classes(group("fFdD"));
        }
    },
    Sign {
        @Override
        public LexerPart createLexerPart() {
            return classes(group("+-"));
        }
    },
    SignedInteger {
        @Override
        public LexerPart createLexerPart() {
            return and(ref(Sign).optional(), ref(Digits));
        }
    },
    BinaryExponent {
        @Override
        public LexerPart createLexerPart() {
            return and(ref(BinaryExponentIndicator), ref(SignedInteger));
        }
    },
    HexadecimalFloatingPointLiteral {
        @Override
        public LexerPart createLexerPart() {
            return and(ref(HexSignificand), ref(BinaryExponent), ref(FloatTypeSuffix).optional());
        }
    },
    ExponentIndicator {
        @Override
        public LexerPart createLexerPart() {
            return classes(group("eE"));
        }
    },
    ExponentPart {
        @Override
        public LexerPart createLexerPart() {
            return and(ref(ExponentIndicator), ref(SignedInteger));
        }
    },
    DecimalFloatingPointLiteral {
        @Override
        public LexerPart createLexerPart() {
            return or(
                    and(
                            ref(Digits),
                            or(
                                    and(
                                            ref(ExponentPart),
                                            ref(FloatTypeSuffix).optional()),
                                    ref(FloatTypeSuffix))),
                    and(
                            or(
                                    and(
                                            ref(Digits),
                                            ref(Dot),
                                            ref(Digits).optional()),
                                    and(
                                            ref(Dot),
                                            ref(Digits))),
                            ref(ExponentPart).optional(),
                            ref(FloatTypeSuffix).optional()));
        }
    },
    FloatingPointLiteral {
        @Override
        public ParseType getParseType() {
            return ParseType.Included;
        }

        @Override
        public LexerPart createLexerPart() {
            return or(ref(DecimalFloatingPointLiteral), ref(HexadecimalFloatingPointLiteral));
        }
    };

    @Override
    public ParseType getParseType() {
        return ParseType.Fragment;
    }
}
