package com.repocleaner.coreclean.languages.java.parser;

import com.repocleaner.parser_gen.ParserPart;
import com.repocleaner.parser_gen.ParserRule;

import static com.repocleaner.parser_gen.ParserPartBuilder.or;
import static com.repocleaner.parser_gen.ParserPartBuilder.ref;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesKeywords.*;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesAnnotations.Annotation;

public enum JavaParserRulesModifiers implements ParserRule {
    Modifier {
        @Override
        public ParserPart createParserPart() {
            return or(
                    "Modifier",
                    ref(Annotation),
                    ref(Public),
                    ref(Protected),
                    ref(Private),
                    ref(Static),
                    ref(Abstract),
                    ref(Final),
                    ref(Native),
                    ref(Synchronized),
                    ref(Transient),
                    ref(Volatile),
                    ref(Strictfp));
        }
    }
}
