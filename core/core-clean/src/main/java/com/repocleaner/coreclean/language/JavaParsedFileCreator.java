package com.repocleaner.coreclean.language;

import com.repocleaner.coreclean.antlr.java.gen.JavaLexer;
import com.repocleaner.coreclean.antlr.java.gen.JavaParser;

public class JavaParsedFileCreator extends ParsedFileCreator {
    public JavaParsedFileCreator() {
        super(JavaLexer::new, JavaParser::new, JavaParser::compilationUnit);
    }
}
