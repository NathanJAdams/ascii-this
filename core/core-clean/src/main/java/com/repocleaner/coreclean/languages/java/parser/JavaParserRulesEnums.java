package com.repocleaner.coreclean.languages.java.parser;

import com.repocleaner.parser_gen.ParserPart;
import com.repocleaner.parser_gen.ParserRule;

import static com.repocleaner.parser_gen.ParserPartBuilder.and;
import static com.repocleaner.parser_gen.ParserPartBuilder.ref;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesIdentifiers.Identifier;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesKeywords.Enum;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesKeywords.Implements;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesSeparators.Comma;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesSeparators.LBrace;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesSeparators.Semi;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesAnnotations.Annotation;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesExpressions.Arguments;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesNormalClasses.ClassBody;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesNormalClasses.ClassBodyDeclaration;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesTypes.TypeList;

public enum JavaParserRulesEnums implements ParserRule {
    EnumDeclaration {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "EnumDeclaration",
                    ref(Enum),
                    ref(Identifier),
                    and(
                            "EnumDeclaration_Implements",
                            ref(Implements),
                            ref(TypeList)).optional(),
                    ref(EnumBody));
        }
    },
    EnumBody {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "EnumBody",
                    ref(LBrace),
                    ref(EnumConstants).optional(),
                    ref(Comma).optional(),
                    ref(EnumBodyDeclarations).optional());
        }
    },
    EnumConstants {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "EnumConstants",
                    ref(EnumConstant),
                    and(
                            "EnumConstants_Extra",
                            ref(Comma),
                            ref(EnumConstant)).any());
        }
    },
    EnumConstant {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "EnumConstant",
                    ref(Annotation).any(),
                    ref(Identifier),
                    ref(Arguments).optional(),
                    ref(ClassBody).optional());
        }
    },
    EnumBodyDeclarations {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "EnumBodyDeclarations",
                    ref(Semi),
                    ref(ClassBodyDeclaration).any());
        }
    }
}
