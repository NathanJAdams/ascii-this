package com.repocleaner.coreclean.languages.java.parser;

import com.repocleaner.parser_gen.ParserPart;
import com.repocleaner.parser_gen.ParserRule;

import static com.repocleaner.parser_gen.ParserPartBuilder.and;
import static com.repocleaner.parser_gen.ParserPartBuilder.or;
import static com.repocleaner.parser_gen.ParserPartBuilder.ref;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesBooleanLiterals.BooleanLiteral;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesCharacterLiterals.CharacterLiteral;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesFloatingPointLiterals.FloatingPointLiteral;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesIdentifiers.Identifier;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesIntegerLiterals.IntegerLiteral;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesKeywords.Class;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesKeywords.Instanceof;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesKeywords.New;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesKeywords.Super;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesKeywords.This;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesKeywords.Void;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesNullLiterals.NullLiteral;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesOperators.*;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesSeparators.Comma;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesSeparators.Dot;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesSeparators.LBrack;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesSeparators.LParen;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesSeparators.RBrack;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesSeparators.RParen;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesStringLiterals.StringLiteral;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesFields.ArrayInitializer;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesNames.QualifiedIdentifier;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesNormalClasses.ClassBody;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesTypes.BasicType;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesTypes.NonWildcardTypeArguments;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesTypes.NonWildcardTypeArgumentsOrDiamond;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesTypes.Type;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesTypes.TypeArgumentsOrDiamond;

public enum JavaParserRulesExpressions implements ParserRule {
    Expression {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "Expression",
                    ref(Expression1),
                    and(
                            "Expression_Assign",
                            ref(AssignmentOperator),
                            ref(Expression1)).optional());
        }
    },
    AssignmentOperator {
        @Override
        public ParserPart createParserPart() {
            return or(
                    "AssignmentOperator",
                    ref(Assign),
                    ref(AddAssign),
                    ref(SubAssign),
                    ref(MulAssign),
                    ref(DivAssign),
                    ref(AndAssign),
                    ref(OrAssign),
                    ref(XorAssign),
                    ref(ModAssign),
                    ref(LShiftAssign),
                    ref(RShiftAssign),
                    ref(URShiftAssign));
        }
    },
    Expression1 {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "Expression1",
                    ref(Expression2),
                    ref(Expression1Rest).optional());
        }
    },
    Expression1Rest {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "Expression1Rest",
                    ref(Question),
                    ref(Expression),
                    ref(Colon),
                    ref(Expression1));
        }
    },
    Expression2 {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "Expression2",
                    ref(Expression3),
                    ref(Expression2Rest).optional());
        }
    },
    Expression2Rest {
        @Override
        public ParserPart createParserPart() {
            return or(
                    "Expression2Rest",
                    and(
                            "Expression2Rest_Infix",
                            ref(InfixOp),
                            ref(Expression3)).any(),
                    and(
                            "Expression2Rest_Instanceof",
                            ref(Instanceof),
                            ref(Type)));
        }
    },
    InfixOp {
        @Override
        public ParserPart createParserPart() {
            return or(
                    "InfixOp",
                    ref(Or),
                    ref(And),
                    ref(BitOr),
                    ref(Caret),
                    ref(BitAnd),
                    ref(Equal),
                    ref(NotEqual),
                    ref(LT),
                    ref(GT),
                    ref(LE),
                    ref(GE),
                    ref(LShift),
                    ref(RShift),
                    ref(URShift),
                    ref(Add),
                    ref(Sub),
                    ref(Mul),
                    ref(Div),
                    ref(Mod));
        }
    },
    Expression3 {
        @Override
        public ParserPart createParserPart() {
            return or(
                    "Expression3",
                    and(
                            "Expression3_Prefix",
                            ref(PrefixOp),
                            ref(Expression3)),
                    and(
                            "Expression3_Expression",
                            ref(LParen),
                            or(
                                    "Expression3_ExpressionType",
                                    ref(Expression),
                                    ref(Type)),
                            ref(RParen),
                            ref(Expression3)),
                    and(
                            "Expression3_SelectorPostfix",
                            ref(Primary),
                            ref(Selector).any(),
                            ref(PostfixOp).any()));
        }
    },
    PrefixOp {
        @Override
        public ParserPart createParserPart() {
            return or(
                    "PrefixOp",
                    ref(Inc),
                    ref(Dec),
                    ref(Bang),
                    ref(Tilde),
                    ref(Add),
                    ref(Sub));
        }
    },
    PostfixOp {
        @Override
        public ParserPart createParserPart() {
            return or(
                    "PostfixOp",
                    ref(Inc),
                    ref(Dec));
        }
    },
    Primary {
        @Override
        public ParserPart createParserPart() {
            return or(
                    "Primary",
                    ref(Literal),
                    ref(ParenthesisedExpression),
                    and(
                            "Primary_This",
                            ref(This),
                            ref(Arguments).optional()),
                    and(
                            "Primary_Super",
                            ref(Super),
                            ref(SuperSuffix)),
                    and(
                            "Primary_Creator",
                            ref(New),
                            ref(Creator)),
                    and(
                            "Primary_Generic",
                            ref(NonWildcardTypeArguments),
                            or(
                                    "Primary_GenericSuffix",
                                    ref(ExplicitGenericInvocationSuffix),
                                    and(
                                            "Primary_GenericSuffixThis",
                                            ref(This),
                                            ref(Arguments)))),
                    and(
                            "Primary_Identifier",
                            ref(QualifiedIdentifier),
                            ref(IdentifierSuffix).optional()),
                    and(
                            "Primary_BasicType",
                            ref(BasicType),
                            ref(Brackets).any(),
                            ref(Dot),
                            ref(Class)),
                    and(
                            "Primary_Void",
                            ref(Void),
                            ref(Dot),
                            ref(Class)));
        }
    },
    Literal {
        @Override
        public ParserPart createParserPart() {
            return or(
                    "Literal",
                    ref(IntegerLiteral),
                    ref(FloatingPointLiteral),
                    ref(CharacterLiteral),
                    ref(StringLiteral),
                    ref(BooleanLiteral),
                    ref(NullLiteral));
        }
    },
    ParenthesisedExpression {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "ParenthesisedExpression",
                    ref(LParen),
                    ref(Expression),
                    ref(RParen));
        }
    },
    Arguments {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "Arguments",
                    ref(LParen),
                    and(
                            "Arguments_Args",
                            ref(Expression),
                            and(
                                    "Arguments_Extra",
                                    ref(Comma),
                                    ref(Expression)).any()).optional(),
                    ref(RParen));
        }
    },
    SuperSuffix {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "SuperSuffix",
                    and(
                            "SuperSuffix_Identifier",
                            ref(Dot),
                            ref(Identifier)).optional(),
                    ref(Arguments));
        }
    },
    ExplicitGenericInvocationSuffix {
        @Override
        public ParserPart createParserPart() {
            return or(
                    "ExplicitGenericInvocationSuffix",
                    and(
                            "ExplicitGenericInvocationSuffix_Super",
                            ref(Super),
                            ref(SuperSuffix)),
                    and(
                            "ExplicitGenericInvocationSuffix_Identifier",
                            ref(Identifier),
                            ref(Arguments)));
        }
    },
    Creator {
        @Override
        public ParserPart createParserPart() {
            return or(
                    "Creator",
                    and(
                            "Creator_Typed",
                            ref(NonWildcardTypeArguments),
                            ref(CreatedName),
                            ref(ClassCreatorRest)),
                    and(
                            "Creator_Untyped",
                            ref(CreatedName),
                            or(
                                    "Creator_UntypedRest",
                                    ref(ClassCreatorRest),
                                    ref(ArrayCreatorRest))));
        }
    },
    CreatedName {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "CreatedName",
                    ref(Type),
                    ref(TypeArgumentsOrDiamond).optional(),
                    and(
                            "CreatedName_Extra",
                            ref(Dot),
                            ref(Identifier),
                            ref(TypeArgumentsOrDiamond)).any());
        }
    },
    ClassCreatorRest {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "ClassCreatorRest",
                    ref(Arguments),
                    ref(ClassBody).optional()
            );
        }
    },
    ArrayCreatorRest {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "ArrayCreatorRest",
                    ref(LBrack),
                    or(
                            "ArrayCreatorRest_FirstBrackets",
                            and(
                                    "ArrayCreatorRest_FirstBracketsInitialiser",
                                    ref(RBrack),
                                    ref(Brackets).any(),
                                    ref(ArrayInitializer)),
                            and(
                                    "ArrayCreatorRest_FirstBracketsExpression",
                                    ref(Expression),
                                    ref(RBrack),
                                    and(
                                            "ArrayCreatorRest_FirstBracketsExpressionExtra",
                                            ref(LBrack),
                                            ref(Expression),
                                            ref(RBrack)).any(),
                                    ref(Brackets).any())));
        }
    },
    IdentifierSuffix {
        @Override
        public ParserPart createParserPart() {
            return or(
                    "IdentifierSuffix",
                    and(
                            "IdentifierSuffix_Array",
                            ref(LBrack),
                            or(
                                    "IdentifierSuffix_ArrayClassOrExpression",
                                    and(
                                            "IdentifierSuffix_ArrayClass",
                                            ref(Brackets).any(),
                                            ref(Dot),
                                            ref(Class)),
                                    ref(Expression)),
                            ref(RBrack)),
                    ref(Arguments),
                    and(
                            "IdentifierSuffix",
                            ref(Dot),
                            or(
                                    "IdentifierSuffix_Other",
                                    ref(Class),
                                    ref(ExplicitGenericInvocation),
                                    ref(This),
                                    and(
                                            "IdentifierSuffix_OtherSuper",
                                            ref(Super),
                                            ref(Arguments)),
                                    and(
                                            "IdentifierSuffix_OtherNew",
                                            ref(New),
                                            ref(NonWildcardTypeArguments).optional(),
                                            ref(InnerCreator)))));
        }
    },
    ExplicitGenericInvocation {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "ExplicitGenericInvocation",
                    ref(NonWildcardTypeArguments),
                    ref(ExplicitGenericInvocationSuffix));
        }
    },
    InnerCreator {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "InnerCreator",
                    ref(Identifier),
                    ref(NonWildcardTypeArgumentsOrDiamond).optional(),
                    ref(ClassCreatorRest));
        }
    },
    Selector {
        @Override
        public ParserPart createParserPart() {
            return or(
                    "Selector",
                    and(
                            "Selector_Dot",
                            ref(Dot),
                            or(
                                    "Selector_DotSuffix",
                                    and(
                                            "Selector_DotSuffixIdentifier",
                                            ref(Identifier),
                                            ref(Arguments).optional()),
                                    ref(ExplicitGenericInvocation),
                                    ref(This),
                                    and(
                                            "Selector_DotSuffixSuper",
                                            ref(Super),
                                            ref(SuperSuffix)),
                                    and(
                                            "Selector_DotSuffixNew",
                                            ref(New),
                                            ref(NonWildcardTypeArguments).optional(),
                                            ref(InnerCreator)))),
                    and(
                            "Selector_Expression",
                            ref(LBrack),
                            ref(Expression),
                            ref(RBrack)));
        }
    },
    Brackets {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "Brackets",
                    ref(LBrack),
                    ref(RBrack));
        }
    }
}
