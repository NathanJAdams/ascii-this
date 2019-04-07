package com.repocleaner.coreclean.languages.java.parser;

import com.repocleaner.parser_gen.ParserPart;
import com.repocleaner.parser_gen.ParserPartBuilder;
import com.repocleaner.parser_gen.ParserRule;

import static com.repocleaner.parser_gen.ParserPartBuilder.and;
import static com.repocleaner.parser_gen.ParserPartBuilder.or;
import static com.repocleaner.parser_gen.ParserPartBuilder.ref;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesIdentifiers.Identifier;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesKeywords.Import;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesKeywords.Package;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesKeywords.Static;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesOperators.Mul;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesSeparators.Dot;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesSeparators.Semi;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesAnnotationTypes.AnnotationTypeDeclaration;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesAnnotations.Annotation;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesEnums.EnumDeclaration;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesModifiers.Modifier;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesNames.DotIdentifier;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesNames.QualifiedIdentifier;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesNormalClasses.NormalClassDeclaration;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesNormalInterfaces.NormalInterfaceDeclaration;

public enum JavaParserRulesPackages implements ParserRule {
    CompilationUnit {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "CompilationUnit",
                    and(
                            "PackageDeclaration",
                            ref(Annotation).any(),
                            ref(Package),
                            ref(QualifiedIdentifier),
                            ref(Semi)).optional(),
                    ref(ImportDeclaration).any(),
                    ref(TypeDeclaration).any());
        }
    },
    ImportDeclaration {
        @Override
        public ParserPart createParserPart() {
            return ParserPartBuilder.and(
                    "ImportDeclaration",
                    ref(Import),
                    ref(Static).optional(),
                    ref(Identifier),
                    ref(DotIdentifier).any(),
                    and(
                            "ImportDeclaration_All",
                            ref(Dot),
                            ref(Mul)).optional(),
                    ref(Semi));
        }
    },
    TypeDeclaration {
        @Override
        public ParserPart createParserPart() {
            return or(
                    "TypeDeclaration",
                    ref(ClassOrInterfaceDeclaration),
                    ref(Semi));
        }
    },
    ClassOrInterfaceDeclaration {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "ClassOrInterfaceDeclaration",
                    ref(Modifier).any(),
                    or(
                            "ClassOrInterfaceDeclaration_Declaration",
                            ref(ClassDeclaration),
                            ref(InterfaceDeclaration)));
        }
    },
    ClassDeclaration {
        @Override
        public ParserPart createParserPart() {
            return or(
                    "ClassDeclaration",
                    ref(NormalClassDeclaration),
                    ref(EnumDeclaration));
        }
    },
    InterfaceDeclaration {
        @Override
        public ParserPart createParserPart() {
            return or(
                    "InterfaceDeclaration",
                    ref(NormalInterfaceDeclaration),
                    ref(AnnotationTypeDeclaration));
        }
    }
}
