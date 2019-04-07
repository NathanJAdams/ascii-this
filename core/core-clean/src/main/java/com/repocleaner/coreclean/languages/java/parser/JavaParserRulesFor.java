package com.repocleaner.coreclean.languages.java.parser;

import com.repocleaner.parser_gen.ParserPart;
import com.repocleaner.parser_gen.ParserRule;

import static com.repocleaner.parser_gen.ParserPartBuilder.and;
import static com.repocleaner.parser_gen.ParserPartBuilder.or;
import static com.repocleaner.parser_gen.ParserPartBuilder.ref;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesOperators.Assign;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesOperators.Colon;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesSeparators.Comma;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesSeparators.Semi;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesExpressions.Expression;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesFields.VariableDeclarator;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesFields.VariableDeclaratorId;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesFields.VariableInitializer;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesModifiers.Modifier;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesTypes.Type;

public enum JavaParserRulesFor implements ParserRule {
    ForControl {
        @Override
        public ParserPart createParserPart() {
            return or(
                    "ForControl",
                    ref(ForVarControl),
                    and(
                            "ForControl_Basic",
                            ref(StatementExpressionList),
                            ref(Semi),
                            ref(Expression).optional(),
                            ref(Semi),
                            ref(StatementExpressionList).optional()));
        }
    },
    ForVarControl {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "ForVarControl",
                    ref(Modifier).any(),
                    ref(Type),
                    ref(VariableDeclaratorId),
                    ref(ForVarControlRest));
        }
    },
    ForVarControlRest {
        @Override
        public ParserPart createParserPart() {
            return or(
                    "ForVarControlRest",
                    and(
                            "ForVarControlRest_Basic",
                            ref(ForVariableDeclaratorsRest),
                            ref(Semi),
                            ref(Expression).optional(),
                            ref(Semi),
                            ref(StatementExpressionList)),
                    and(
                            "ForVarControlRest_Enhanced",
                            ref(Colon),
                            ref(Expression)));
        }
    },
    ForVariableDeclaratorsRest {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "ForVariableDeclaratorsRest",
                    and(
                            "ForVariableDeclaratorsRest_Assign",
                            ref(Assign),
                            ref(VariableInitializer)).optional(),
                    and(
                            "ForVariableDeclaratorsRest_Extra",
                            ref(Comma),
                            ref(VariableDeclarator)).any());
        }
    },
    StatementExpressionList {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "StatementExpressionList",
                    ref(Expression),
                    and(
                            "StatementExpressionList_Extra",
                            ref(Comma),
                            ref(Expression)).any());
        }
    }
}
