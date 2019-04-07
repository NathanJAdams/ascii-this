package com.repocleaner.coreclean.languages.java.parser;

import com.repocleaner.parser_gen.ParserPart;
import com.repocleaner.parser_gen.ParserRule;

import static com.repocleaner.parser_gen.ParserPartBuilder.and;
import static com.repocleaner.parser_gen.ParserPartBuilder.or;
import static com.repocleaner.parser_gen.ParserPartBuilder.ref;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesIdentifiers.Identifier;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesKeywords.Extends;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesKeywords.Interface;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesKeywords.Throws;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesKeywords.Void;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesOperators.Assign;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesSeparators.LBrace;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesSeparators.RBrace;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesSeparators.Semi;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesExpressions.Brackets;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesFields.VariableInitializer;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesModifiers.Modifier;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesNames.QualifiedIdentifierList;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesPackages.ClassDeclaration;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesPackages.InterfaceDeclaration;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesParameters.FormalParameters;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesTypes.Type;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesTypes.TypeList;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesTypes.TypeParameters;

public enum JavaParserRulesNormalInterfaces implements ParserRule {
    NormalInterfaceDeclaration {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "NormalInterfaceDeclaration",
                    ref(Interface),
                    ref(Identifier),
                    ref(TypeParameters).optional(),
                    and(
                            "NormalInterfaceDeclaration_Extends",
                            ref(Extends),
                            ref(TypeList)).optional(),
                    ref(InterfaceBody));
        }
    },
    InterfaceBody {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "InterfaceBody",
                    ref(LBrace),
                    ref(InterfaceBodyDeclaration),
                    ref(RBrace));
        }
    },
    InterfaceBodyDeclaration {
        @Override
        public ParserPart createParserPart() {
            return or(
                    "InterfaceBodyDeclaration",
                    ref(Semi),
                    and(
                            "InterfaceBodyDeclaration_Member",
                            ref(Modifier).any(),
                            ref(InterfaceMemberDeclaration)));
        }
    },
    InterfaceMemberDeclaration {
        @Override
        public ParserPart createParserPart() {
            return or(
                    "InterfaceMemberDeclaration",
                    ref(InterfaceMethodOrFieldDeclaration),
                    and(
                            "InterfaceMemberDeclaration_Void",
                            ref(Void),
                            ref(Identifier),
                            ref(VoidInterfaceMethodDeclaratorRest)),
                    ref(InterfaceGenericMethodDeclaration),
                    ref(ClassDeclaration),
                    ref(InterfaceDeclaration)
            );
        }
    },
    InterfaceMethodOrFieldDeclaration {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "InterfaceMethodOrFieldDeclaration",
                    ref(Type),
                    ref(Identifier),
                    ref(InterfaceMethodOrFieldRest));
        }
    },
    InterfaceMethodOrFieldRest {
        @Override
        public ParserPart createParserPart() {
            return or(
                    "InterfaceMethodOrFieldRest",
                    and(
                            "InterfaceMethodOrFieldRest_Constant",
                            ref(ConstantDeclaratorsRest),
                            ref(Semi)),
                    ref(InterfaceMethodDeclaratorRest));
        }
    },
    ConstantDeclaratorsRest {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "ConstantDeclaratorsRest",
                    ref(ConstantDeclaratorRest),
                    and(
                            "ConstantDeclaratorsRest_Extra",
                            ref(Semi),
                            ref(ConstantDeclarator)));
        }
    },
    ConstantDeclaratorRest {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "ConstantDeclaratorRest",
                    ref(Brackets).any(),
                    ref(Assign),
                    ref(VariableInitializer));
        }
    },
    ConstantDeclarator {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "ConstantDeclarator",
                    ref(Identifier),
                    ref(ConstantDeclaratorRest));
        }
    },
    InterfaceMethodDeclaratorRest {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "InterfaceMethodDeclaratorRest",
                    ref(FormalParameters),
                    ref(Brackets).any(),
                    and(
                            "InterfaceMethodDeclaratorRest_Throws",
                            ref(Throws),
                            ref(QualifiedIdentifierList)).optional(),
                    ref(Semi));
        }
    },
    VoidInterfaceMethodDeclaratorRest {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "VoidInterfaceMethodDeclaratorRest",
                    ref(FormalParameters),
                    and(
                            "VoidInterfaceMethodDeclaratorRest_Throws",
                            ref(Throws),
                            ref(QualifiedIdentifierList)).optional(),
                    ref(Semi));
        }
    },
    InterfaceGenericMethodDeclaration {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "InterfaceGenericMethodDeclaration",
                    ref(TypeParameters),
                    or(
                            "InterfaceGenericMethodDeclaration_Type",
                            ref(Type),
                            ref(Void)),
                    ref(Identifier),
                    ref(InterfaceMethodDeclaratorRest));
        }
    }
}
