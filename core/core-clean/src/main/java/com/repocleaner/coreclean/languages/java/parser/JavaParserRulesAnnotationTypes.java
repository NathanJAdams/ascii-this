package com.repocleaner.coreclean.languages.java.parser;

import com.repocleaner.parser_gen.ParserPart;
import com.repocleaner.parser_gen.ParserRule;

import static com.repocleaner.parser_gen.ParserPartBuilder.and;
import static com.repocleaner.parser_gen.ParserPartBuilder.or;
import static com.repocleaner.parser_gen.ParserPartBuilder.ref;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesIdentifiers.Identifier;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesKeywords.Default;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesKeywords.Interface;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesNonCodeSymbols.At;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesSeparators.LBrace;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesSeparators.LParen;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesSeparators.RBrace;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesSeparators.RParen;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesAnnotations.ElementValue;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesEnums.EnumDeclaration;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesExpressions.Brackets;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesModifiers.Modifier;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesNormalInterfaces.ConstantDeclaratorsRest;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesPackages.ClassDeclaration;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesPackages.InterfaceDeclaration;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesTypes.Type;

public enum JavaParserRulesAnnotationTypes implements ParserRule {
    AnnotationTypeDeclaration {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "AnnotationTypeDeclaration",
                    ref(At),
                    ref(Interface),
                    ref(Identifier),
                    ref(AnnotationTypeBody));
        }
    },
    AnnotationTypeBody {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "AnnotationTypeBody",
                    ref(LBrace),
                    ref(AnnotationTypeElementDeclaration).any(),
                    ref(RBrace));
        }
    },
    AnnotationTypeElementDeclaration {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "AnnotationTypeElementDeclaration",
                    ref(Modifier).any(),
                    ref(AnnotationTypeElementRest));
        }
    },
    AnnotationTypeElementRest {
        @Override
        public ParserPart createParserPart() {
            return or(
                    "AnnotationTypeElementRest",
                    and(
                            "AnnotationTypeElementRest_MethodOrConstant",
                            ref(Type),
                            ref(Identifier),
                            ref(AnnotationMethodOrConstantRest)),
                    ref(ClassDeclaration),
                    ref(InterfaceDeclaration),
                    ref(EnumDeclaration),
                    ref(AnnotationTypeDeclaration));
        }
    },
    AnnotationMethodOrConstantRest {
        @Override
        public ParserPart createParserPart() {
            return or(
                    "AnnotationMethodOrConstantRest",
                    ref(AnnotationMethodRest),
                    ref(ConstantDeclaratorsRest));
        }
    },
    AnnotationMethodRest {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "AnnotationMethodRest",
                    ref(LParen),
                    ref(RParen),
                    ref(Brackets).optional(),
                    and(
                            "AnnotationMethodRest_Default",
                            ref(Default),
                            ref(ElementValue)));
        }
    }
}
