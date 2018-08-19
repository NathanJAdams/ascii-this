package com.repocleaner.clean.language;

import com.repocleaner.clean.antlr.java.gen.JavaLexer;
import com.repocleaner.clean.antlr.java.gen.JavaParser;

public class JavaParsedFileCreator extends ParsedFileCreator {
    public JavaParsedFileCreator() {
        super(JavaLexer::new, JavaParser::new, JavaParser::compilationUnit);
    }
}
