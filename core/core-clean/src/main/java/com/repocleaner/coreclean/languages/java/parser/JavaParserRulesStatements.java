package com.repocleaner.coreclean.languages.java.parser;

import com.repocleaner.parser_gen.ParserPart;
import com.repocleaner.parser_gen.ParserRule;

import static com.repocleaner.parser_gen.ParserPartBuilder.and;
import static com.repocleaner.parser_gen.ParserPartBuilder.or;
import static com.repocleaner.parser_gen.ParserPartBuilder.ref;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesIdentifiers.Identifier;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesKeywords.*;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesOperators.Assign;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesOperators.BitOr;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesOperators.Colon;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesSeparators.LBrace;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesSeparators.LParen;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesSeparators.RBrace;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesSeparators.RParen;
import static com.repocleaner.coreclean.languages.java.lexer.JavaLexerRulesSeparators.Semi;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesExpressions.Expression;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesExpressions.ParenthesisedExpression;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesFields.VariableDeclaratorId;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesFields.VariableDeclarators;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesFor.ForControl;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesModifiers.Modifier;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesNames.QualifiedIdentifier;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesPackages.ClassOrInterfaceDeclaration;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesSwitch.SwitchBlockStatementGroup;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesTypes.ReferenceType;
import static com.repocleaner.coreclean.languages.java.parser.JavaParserRulesTypes.Type;

public enum JavaParserRulesStatements implements ParserRule {
    Block {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "Block",
                    ref(LBrace),
                    ref(BlockStatement).any(),
                    ref(RBrace));
        }
    },
    BlockStatement {
        @Override
        public ParserPart createParserPart() {
            return or(
                    "BlockStatement",
                    ref(LocalVariableDeclarationStatement),
                    ref(ClassOrInterfaceDeclaration),
                    and(
                            "BlockStatement_Labelled",
                            and(
                                    "BlockStatement_Label",
                                    ref(Identifier),
                                    ref(Colon)).optional(),
                            ref(Statement)));
        }
    },
    LocalVariableDeclarationStatement {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "LocalVariableDeclarationStatement",
                    ref(Modifier).any(),
                    ref(Type),
                    ref(VariableDeclarators),
                    ref(Semi));
        }
    },
    Statement {
        @Override
        public ParserPart createParserPart() {
            return or(
                    "Statement",
                    ref(Block),
                    ref(Semi),
                    and(
                            "Statement_Identifier",
                            ref(Identifier),
                            ref(Colon),
                            ref(Statement)),
                    and(
                            "Statement_Expression",
                            ref(Expression),
                            ref(Semi)),
                    and(
                            "Statement_If",
                            ref(If),
                            ref(ParenthesisedExpression),
                            ref(Statement),
                            and(
                                    "Statement_IfElse",
                                    ref(Else),
                                    ref(Statement)).optional()),
                    and(
                            "Statement_Assert",
                            ref(Assert),
                            ref(Expression),
                            and(
                                    "Statement_AssertSecond",
                                    ref(Colon),
                                    ref(Expression)).optional(),
                            ref(Semi)),
                    and(
                            "Statement_Switch",
                            ref(Switch),
                            ref(ParenthesisedExpression),
                            ref(LBrace),
                            ref(SwitchBlockStatementGroup).any(),
                            ref(RBrace)),
                    and(
                            "Statement_While",
                            ref(While),
                            ref(ParenthesisedExpression),
                            ref(Statement)),
                    and(
                            "Statement_Do",
                            ref(Do),
                            ref(Statement),
                            ref(While),
                            ref(ParenthesisedExpression),
                            ref(Semi)),
                    and(
                            "Statement_For",
                            ref(For),
                            ref(LParen),
                            ref(ForControl),
                            ref(RParen),
                            ref(Statement)),
                    and(
                            "Statement_Break",
                            ref(Break),
                            ref(Identifier).optional(),
                            ref(Semi)),
                    and(
                            "Statement_Continue",
                            ref(Continue),
                            ref(Identifier).optional(),
                            ref(Semi)),
                    and(
                            "Statement_Return",
                            ref(Return),
                            ref(Expression).optional(),
                            ref(Semi)),
                    and(
                            "Statement_Throw",
                            ref(Throw),
                            ref(Expression),
                            ref(Semi)),
                    and(
                            "Statement_Synchronised",
                            ref(Synchronized),
                            ref(ParenthesisedExpression),
                            ref(Block)),
                    and(
                            "Statement_TryBasic",
                            ref(Try),
                            ref(Block),
                            or(
                                    "Statement_TryBasicCatchesFinally",
                                    ref(CatchClause).many(),
                                    and(
                                            "Statement_TryBasicBoth",
                                            ref(Catch).any(),
                                            ref(FinallyBlock)))),
                    and(
                            "Statement_TryWithResources",
                            ref(Try),
                            ref(ResourceSpecification),
                            ref(Block),
                            ref(CatchClause).any(),
                            ref(FinallyBlock).optional()));
        }
    },
    CatchClause {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "CatchClause",
                    ref(Catch),
                    ref(LParen),
                    ref(Modifier).any(),
                    ref(CatchType),
                    ref(Identifier),
                    ref(RParen),
                    ref(Block));
        }
    },
    CatchType {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "CatchType",
                    ref(QualifiedIdentifier),
                    and(
                            "CatchType_Extra",
                            ref(BitOr),
                            ref(QualifiedIdentifier)).any());
        }
    },
    FinallyBlock {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "FinallyBlock",
                    ref(Finally),
                    ref(Block));
        }
    },
    ResourceSpecification {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "ResourceSpecification",
                    ref(LParen),
                    ref(Resources),
                    ref(Semi).optional(),
                    ref(RParen));
        }
    },
    Resources {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "Resources",
                    ref(Resource),
                    and(
                            "Resources_Extra",
                            ref(Semi),
                            ref(Resource)).any());
        }
    },
    Resource {
        @Override
        public ParserPart createParserPart() {
            return and(
                    "Resource",
                    ref(Modifier).any(),
                    ref(ReferenceType),
                    ref(VariableDeclaratorId),
                    ref(Assign),
                    ref(Expression));
        }
    }
}
