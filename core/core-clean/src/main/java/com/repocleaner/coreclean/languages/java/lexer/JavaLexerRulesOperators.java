package com.repocleaner.coreclean.languages.java.lexer;

import com.repocleaner.parser_gen.LexerPart;
import com.repocleaner.parser_gen.LexerRule;

import static com.repocleaner.parser_gen.LexerPartBuilder.literal;

public enum JavaLexerRulesOperators implements LexerRule {
    URShiftAssign {
        @Override
        public LexerPart createLexerPart() {
            return literal(">>>=");
        }
    },
    LShiftAssign {
        @Override
        public LexerPart createLexerPart() {
            return literal("<<=");
        }
    },
    RShiftAssign {
        @Override
        public LexerPart createLexerPart() {
            return literal(">>=");
        }
    },
    URShift {
        @Override
        public LexerPart createLexerPart() {
            return literal(">>>");
        }
    },
    LShift {
        @Override
        public LexerPart createLexerPart() {
            return literal("<<");
        }
    },
    RShift {
        @Override
        public LexerPart createLexerPart() {
            return literal(">>");
        }
    },
    Equal {
        @Override
        public LexerPart createLexerPart() {
            return literal("==");
        }
    },
    AddAssign {
        @Override
        public LexerPart createLexerPart() {
            return literal("+=");
        }
    },
    SubAssign {
        @Override
        public LexerPart createLexerPart() {
            return literal("-=");
        }
    },
    MulAssign {
        @Override
        public LexerPart createLexerPart() {
            return literal("*=");
        }
    },
    DivAssign {
        @Override
        public LexerPart createLexerPart() {
            return literal("/=");
        }
    },
    AndAssign {
        @Override
        public LexerPart createLexerPart() {
            return literal("&=");
        }
    },
    OrAssign {
        @Override
        public LexerPart createLexerPart() {
            return literal("|=");
        }
    },
    XorAssign {
        @Override
        public LexerPart createLexerPart() {
            return literal("^=");
        }
    },
    ModAssign {
        @Override
        public LexerPart createLexerPart() {
            return literal("%=");
        }
    },
    LE {
        @Override
        public LexerPart createLexerPart() {
            return literal("<=");
        }
    },
    GE {
        @Override
        public LexerPart createLexerPart() {
            return literal(">=");
        }
    },
    NotEqual {
        @Override
        public LexerPart createLexerPart() {
            return literal("!=");
        }
    },
    And {
        @Override
        public LexerPart createLexerPart() {
            return literal("&&");
        }
    },
    Or {
        @Override
        public LexerPart createLexerPart() {
            return literal("||");
        }
    },
    Inc {
        @Override
        public LexerPart createLexerPart() {
            return literal("++");
        }
    },
    Dec {
        @Override
        public LexerPart createLexerPart() {
            return literal("--");
        }
    },
    ColonColon {
        @Override
        public LexerPart createLexerPart() {
            return literal("::");
        }
    },
    Arrow {
        @Override
        public LexerPart createLexerPart() {
            return literal("->");
        }
    },
    GT {
        @Override
        public LexerPart createLexerPart() {
            return literal(">");
        }
    },
    LT {
        @Override
        public LexerPart createLexerPart() {
            return literal("<");
        }
    },
    Assign {
        @Override
        public LexerPart createLexerPart() {
            return literal("=");
        }
    },
    Add {
        @Override
        public LexerPart createLexerPart() {
            return literal("+");
        }
    },
    Sub {
        @Override
        public LexerPart createLexerPart() {
            return literal("-");
        }
    },
    Mul {
        @Override
        public LexerPart createLexerPart() {
            return literal("*");
        }
    },
    Div {
        @Override
        public LexerPart createLexerPart() {
            return literal("/");
        }
    },
    BitAnd {
        @Override
        public LexerPart createLexerPart() {
            return literal("&");
        }
    },
    BitOr {
        @Override
        public LexerPart createLexerPart() {
            return literal("|");
        }
    },
    Caret {
        @Override
        public LexerPart createLexerPart() {
            return literal("^");
        }
    },
    Mod {
        @Override
        public LexerPart createLexerPart() {
            return literal("%");
        }
    },
    Bang {
        @Override
        public LexerPart createLexerPart() {
            return literal("!");
        }
    },
    Tilde {
        @Override
        public LexerPart createLexerPart() {
            return literal("~");
        }
    },
    Question {
        @Override
        public LexerPart createLexerPart() {
            return literal("?");
        }
    },
    Colon {
        @Override
        public LexerPart createLexerPart() {
            return literal(":");
        }
    }
}
