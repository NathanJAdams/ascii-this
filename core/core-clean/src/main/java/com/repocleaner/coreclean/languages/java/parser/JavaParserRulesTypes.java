package com.repocleaner.coreclean.languages.java.parser;

import com.repocleaner.parser_gen.ParserPart;
import com.repocleaner.parser_gen.ParserRule;

import static com.repocleaner.parser_gen.ParserPartBuilder.and;
import static com.repocleaner.parser_gen.ParserPartBuilder.or;
import static com.repocleaner.parser_gen.ParserPartBuilder.ref;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesIdentifiers.Identifier;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesKeywords.Extends;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesKeywords.Super;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesOperators.BitAnd;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesOperators.GT;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesOperators.LT;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesOperators.Question;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesPrimitiveTypes.BooleanType;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesPrimitiveTypes.FloatingPointType;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesPrimitiveTypes.IntegralType;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesSeparators.Comma;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesSeparators.Dot;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesExpressions.Brackets;

public enum JavaParserRulesTypes implements ParserRule {
    Type {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "Type",
                    or(
                            "Type_BasicReference",
                            ref(BasicType),
                            ref(ReferenceType)),
                    ref(Brackets).any());
        }
    },
    BasicType {
        @Override
        public ParserPart createParserPart() {
            return or(
                    "BasicType",
                    ref(IntegralType),
                    ref(FloatingPointType),
                    ref(BooleanType));
        }
    },
    ReferenceType {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "ReferenceType",
                    ref(Identifier),
                    ref(TypeArguments).optional(),
                    and(
                            "ReferenceType_Extra",
                            ref(Dot),
                            ref(Identifier),
                            ref(TypeArguments).optional()).any());
        }
    },
    TypeArguments {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "TypeArguments",
                    ref(LT),
                    ref(TypeArgument),
                    and(
                            "TypeArguments_Extra",
                            ref(Comma),
                            ref(TypeArgument)).any(),
                    ref(GT));
        }
    },
    TypeArgument {
        @Override
        public ParserPart createParserPart() {
            return or(
                    "TypeArgument",
                    ref(ReferenceType),
                    and(
                            "TypeArgument_Bounded",
                            ref(Question),
                            and(
                                    "TypeArgument_BoundedBounds",
                                    or(
                                            "TypeArgument_BoundedBoundsDirection",
                                            ref(Extends),
                                            ref(Super)),
                                    ref(ReferenceType)).optional()));
        }
    },
    NonWildcardTypeArguments {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "NonWildcardTypeArguments",
                    ref(LT),
                    ref(TypeList),
                    ref(GT));
        }
    },
    TypeList {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "TypeList",
                    ref(ReferenceType),
                    and(
                            "TypeList_Extra",
                            ref(Comma),
                            ref(ReferenceType)).any());
        }
    },
    TypeArgumentsOrDiamond {
        @Override
        public ParserPart createParserPart() {
            return or(
                    "TypeArgumentsOrDiamond",
                    and(
                            "AngleBrackets",
                            ref(LT),
                            ref(GT)),
                    ref(TypeArguments));
        }
    },
    NonWildcardTypeArgumentsOrDiamond {
        @Override
        public ParserPart createParserPart() {
            return or(
                    "NonWildcardTypeArgumentsOrDiamond",
                    and(
                            "AngleBrackets",
                            ref(LT),
                            ref(GT)),
                    ref(NonWildcardTypeArguments));
        }
    },
    TypeParameters {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "TypeParameters",
                    ref(LT),
                    ref(TypeParameter),
                    and(
                            "TypeParameters_Extra",
                            ref(Comma),
                            ref(TypeParameter)).any(),
                    ref(GT));
        }
    },
    TypeParameter {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "TypeParameter",
                    ref(Identifier),
                    and(
                            "TypeParameter_Extends",
                            ref(Extends),
                            ref(Bound)));
        }
    },
    Bound {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "Bound",
                    ref(ReferenceType),
                    and(
                            "Bound_Extra",
                            ref(BitAnd),
                            ref(ReferenceType)).any());
        }
    }
}
