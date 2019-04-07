package com.repocleaner.coreclean.languages.java.lexer;

import com.repocleaner.parser_gen.LexerPart;
import com.repocleaner.parser_gen.LexerRule;

import static com.repocleaner.parser_gen.LexerPartBuilder.literal;

public enum JavaLexerRulesKeywords implements LexerRule {
    Abstract {
        @Override
        public LexerPart createLexerPart() {
            return literal("abstract");
        }
    },
    Assert {
        @Override
        public LexerPart createLexerPart() {
            return literal("assert");
        }
    },
    Break {
        @Override
        public LexerPart createLexerPart() {
            return literal("break");
        }
    },
    Case {
        @Override
        public LexerPart createLexerPart() {
            return literal("case");
        }
    },
    Catch {
        @Override
        public LexerPart createLexerPart() {
            return literal("catch");
        }
    },
    Class {
        @Override
        public LexerPart createLexerPart() {
            return literal("class");
        }
    },
    Const {
        @Override
        public LexerPart createLexerPart() {
            return literal("const");
        }
    },
    Continue {
        @Override
        public LexerPart createLexerPart() {
            return literal("continue");
        }
    },
    Default {
        @Override
        public LexerPart createLexerPart() {
            return literal("default");
        }
    },
    Do {
        @Override
        public LexerPart createLexerPart() {
            return literal("do");
        }
    },
    Else {
        @Override
        public LexerPart createLexerPart() {
            return literal("else");
        }
    },
    Enum {
        @Override
        public LexerPart createLexerPart() {
            return literal("enum");
        }
    },
    Extends {
        @Override
        public LexerPart createLexerPart() {
            return literal("extends");
        }
    },
    Final {
        @Override
        public LexerPart createLexerPart() {
            return literal("final");
        }
    },
    Finally {
        @Override
        public LexerPart createLexerPart() {
            return literal("finally");
        }
    },
    For {
        @Override
        public LexerPart createLexerPart() {
            return literal("for");
        }
    },
    Goto {
        @Override
        public LexerPart createLexerPart() {
            return literal("goto");
        }
    },
    If {
        @Override
        public LexerPart createLexerPart() {
            return literal("if");
        }
    },
    Implements {
        @Override
        public LexerPart createLexerPart() {
            return literal("implements");
        }
    },
    Import {
        @Override
        public LexerPart createLexerPart() {
            return literal("import");
        }
    },
    Instanceof {
        @Override
        public LexerPart createLexerPart() {
            return literal("instanceof");
        }
    },
    Interface {
        @Override
        public LexerPart createLexerPart() {
            return literal("interface");
        }
    },
    Native {
        @Override
        public LexerPart createLexerPart() {
            return literal("native");
        }
    },
    New {
        @Override
        public LexerPart createLexerPart() {
            return literal("new");
        }
    },
    Package {
        @Override
        public LexerPart createLexerPart() {
            return literal("package");
        }
    },
    Private {
        @Override
        public LexerPart createLexerPart() {
            return literal("private");
        }
    },
    Protected {
        @Override
        public LexerPart createLexerPart() {
            return literal("protected");
        }
    },
    Public {
        @Override
        public LexerPart createLexerPart() {
            return literal("public");
        }
    },
    Return {
        @Override
        public LexerPart createLexerPart() {
            return literal("return");
        }
    },
    Static {
        @Override
        public LexerPart createLexerPart() {
            return literal("static");
        }
    },
    Strictfp {
        @Override
        public LexerPart createLexerPart() {
            return literal("strictfp");
        }
    },
    Super {
        @Override
        public LexerPart createLexerPart() {
            return literal("super");
        }
    },
    Switch {
        @Override
        public LexerPart createLexerPart() {
            return literal("switch");
        }
    },
    Synchronized {
        @Override
        public LexerPart createLexerPart() {
            return literal("synchronized");
        }
    },
    This {
        @Override
        public LexerPart createLexerPart() {
            return literal("this");
        }
    },
    Throw {
        @Override
        public LexerPart createLexerPart() {
            return literal("throw");
        }
    },
    Throws {
        @Override
        public LexerPart createLexerPart() {
            return literal("throws");
        }
    },
    Transient {
        @Override
        public LexerPart createLexerPart() {
            return literal("transient");
        }
    },
    Try {
        @Override
        public LexerPart createLexerPart() {
            return literal("try");
        }
    },
    Void {
        @Override
        public LexerPart createLexerPart() {
            return literal("void");
        }
    },
    Volatile {
        @Override
        public LexerPart createLexerPart() {
            return literal("volatile");
        }
    },
    While {
        @Override
        public LexerPart createLexerPart() {
            return literal("while");
        }
    }
}
