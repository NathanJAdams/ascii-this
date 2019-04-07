package com.repocleaner.coreclean.languages.java.parser;

import com.repocleaner.parser_gen.ParserPart;
import com.repocleaner.parser_gen.ParserRule;

import static com.repocleaner.parser_gen.ParserPartBuilder.and;
import static com.repocleaner.parser_gen.ParserPartBuilder.or;
import static com.repocleaner.parser_gen.ParserPartBuilder.ref;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesIdentifiers.Identifier;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesKeywords.Class;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesKeywords.Extends;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesKeywords.Implements;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesKeywords.Static;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesKeywords.Throws;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesKeywords.Void;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesSeparators.Comma;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesSeparators.LBrace;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesSeparators.RBrace;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesSeparators.Semi;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesExpressions.Brackets;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesFields.VariableDeclarator;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesFields.VariableDeclaratorRest;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesModifiers.Modifier;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesNames.QualifiedIdentifierList;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesPackages.ClassDeclaration;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesPackages.InterfaceDeclaration;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesParameters.FormalParameters;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesStatements.Block;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesTypes.Type;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesTypes.TypeList;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesTypes.TypeParameters;

public enum JavaParserRulesNormalClasses implements ParserRule {
    NormalClassDeclaration {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "NormalClassDeclaration",
                    ref(Class),
                    ref(Identifier),
                    ref(TypeParameters).optional(),
                    and(
                            "NormalClassDeclaration_Extends",
                            ref(Extends),
                            ref(Type)).optional(),
                    and(
                            "NormalClassDeclaration_Implements",
                            ref(Implements),
                            ref(TypeList)).optional(),
                    ref(ClassBody));
        }
    },
    ClassBody {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "ClassBody",
                    ref(LBrace),
                    ref(ClassBodyDeclaration).any(),
                    ref(RBrace));
        }
    },
    ClassBodyDeclaration {
        @Override
        public ParserPart createParserPart() {
            return or(
                    "ClassBodyDeclaration",
                    ref(Semi),
                    and(
                            "ClassBodyDeclaration_Member",
                            ref(Modifier).any(),
                            ref(MemberDeclaration)),
                    and(
                            "ClassBodyDeclaration_Block",
                            ref(Static).optional(),
                            ref(Block)));
        }
    },
    MemberDeclaration {
        @Override
        public ParserPart createParserPart() {
            return or(
                    "MemberDeclaration",
                    ref(MethodOrFieldDeclaration),
                    and(
                            "MemberDeclaration_Void",
                            ref(Void),
                            ref(Identifier),
                            ref(VoidMethodDeclaratorRest)),
                    and(
                            "MemberDeclaration_Constructor",
                            ref(Identifier),
                            ref(ConstructorDeclaratorRest)),
                    ref(GenericMethodOrConstructorDeclaration),
                    ref(ClassDeclaration),
                    ref(InterfaceDeclaration));
        }
    },
    MethodOrFieldDeclaration {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "MethodOrFieldDeclaration",
                    ref(Type),
                    ref(Identifier),
                    ref(MethodOrFieldRest));
        }
    },
    MethodOrFieldRest {
        @Override
        public ParserPart createParserPart() {
            return or(
                    "MemberOrFieldRest",
                    and(
                            "MemberOrFieldRest_Field",
                            ref(FieldDeclaratorsRest),
                            ref(Semi)),
                    ref(MethodDeclaratorRest));
        }
    },
    FieldDeclaratorsRest {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "FieldDeclaratorsRest",
                    ref(VariableDeclaratorRest),
                    and(
                            "FieldDeclaratorsRest_Extra",
                            ref(Comma),
                            ref(VariableDeclarator)).any());
        }
    },
    MethodDeclaratorRest {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "MethodDeclaratorRest",
                    ref(FormalParameters),
                    ref(Brackets).any(),
                    and(
                            "MethodDeclaratorRest_Throws",
                            ref(Throws),
                            ref(QualifiedIdentifierList)).optional(),
                    or(
                            "MethodDeclaratorRest_Block",
                            ref(Block),
                            ref(Semi)));
        }
    },
    VoidMethodDeclaratorRest {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "VoidMethodDeclaratorRest",
                    ref(FormalParameters),
                    and(
                            "VoidMethodDeclaratorRest_Throws",
                            ref(Throws),
                            ref(QualifiedIdentifierList)).optional(),
                    or(
                            "VoidMethodDeclaratorRest_Block",
                            ref(Block),
                            ref(Semi)));
        }
    },
    ConstructorDeclaratorRest {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "ConstructorDeclaratorRest",
                    ref(FormalParameters),
                    and(
                            "ConstructorDeclaratorRest_Throws",
                            ref(Throws),
                            ref(QualifiedIdentifierList)).optional(),
                    ref(Block));
        }
    },
    GenericMethodOrConstructorDeclaration {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "GenericMethodOrConstructorDeclaration",
                    ref(TypeParameters),
                    ref(GenericMethodOrConstructorRest));
        }
    },
    GenericMethodOrConstructorRest {
        @Override
        public ParserPart createParserPart() {
            return or(
                    "GenericMethodOrConstructorRest",
                    and(
                            "GenericMethodOrConstructorRest_Method",
                            or(
                                    "GenericMethodOrConstructorRest_MethodType",
                                    ref(Type),
                                    ref(Void)),
                            ref(Identifier),
                            ref(MethodDeclaratorRest)),
                    and(
                            "GenericMethodOrConstructorRest_Constructor",
                            ref(Identifier),
                            ref(ConstructorDeclaratorRest)));
        }
    }
}
