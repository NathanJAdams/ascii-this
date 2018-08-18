package clean.language;

import clean.antlr.java.gen.JavaLexer;
import clean.antlr.java.gen.JavaParser;

public class JavaParsedFileCreator extends ParsedFileCreator {
    public JavaParsedFileCreator() {
        super(JavaLexer::new, JavaParser::new, JavaParser::compilationUnit);
    }
}
