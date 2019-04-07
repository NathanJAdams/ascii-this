package com.repocleaner.coreclean.languages.java.parser;

import com.repocleaner.parser_gen.ParserPart;
import com.repocleaner.parser_gen.ParserRule;

import static com.repocleaner.parser_gen.ParserPartBuilder.and;
import static com.repocleaner.parser_gen.ParserPartBuilder.or;
import static com.repocleaner.parser_gen.ParserPartBuilder.ref;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesIdentifiers.Identifier;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesKeywords.Case;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesKeywords.Default;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesOperators.Colon;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesExpressions.Expression;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesStatements.BlockStatement;

public enum JavaParserRulesSwitch implements ParserRule {
    SwitchBlockStatementGroup {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "SwitchBlockStatementGroup",
                    ref(SwitchLabel).many(),
                    ref(BlockStatement).any());
        }
    },
    SwitchLabel {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "SwitchLabel",
                    or(
                            "SwitchLabel_CaseOrDefault",
                            ref(Default),
                            and(
                                    "SwitchLabel_Case",
                                    ref(Case),
                                    or(
                                            "SwitchLabel_CaseSpecific",
                                            ref(Expression),
                                            ref(Identifier)))),
                    ref(Colon));
        }
    }
}
