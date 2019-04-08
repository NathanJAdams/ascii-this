package com.repocleaner.coreclean.language;

import com.repocleaner.coreclean.languages.java.JavaGrammarCreator;
import com.repocleaner.coreclean.languages.java.parser.JavaParserRulesPackages;
import com.repocleaner.coreclean.transform.Contextualiser;
import com.repocleaner.coreclean.transform.java.JavaContextualiser;
import com.repocleaner.parser_gen.Grammar;
import com.repocleaner.parser_gen.GrammarCreator;
import com.repocleaner.parser_gen.ParserRule;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;

public enum Language {
    Java(new JavaGrammarCreator(), JavaParserRulesPackages.CompilationUnit, new JavaContextualiser(), "java");

    private static final Map<String, Language> EXTENSION_LANGUAGES = new HashMap<>();

    static {
        for (Language language : values()) {
            for (String extension : language.extensions) {
                EXTENSION_LANGUAGES.put(extension, language);
            }
        }
    }

    @Getter
    private final Grammar grammar;
    @Getter
    private final ParserRule fileParserRule;
    @Getter
    private final Contextualiser contextualiser;
    private final List<String> extensions;

    Language(GrammarCreator grammarCreator, ParserRule fileParserRule, Contextualiser contextualiser, String... extensions) {
        this.grammar = grammarCreator.create();
        this.fileParserRule = fileParserRule;
        this.contextualiser = contextualiser;
        this.extensions = Arrays.asList(extensions);
    }

    public static Language fromFile(File file) {
        String filePath = file.getPath();
        return fromFilePath(filePath);
    }

    public static Language fromFilePath(String filePath) {
        if (filePath == null || filePath.length() == 0) {
            return null;
        }
        int lastDotIndex = filePath.lastIndexOf('.');
        // if there is no extension this will just become the file name itself
        String extension = filePath.substring(lastDotIndex + 1);
        return getFromExtension(extension);
    }

    public static Language getFromExtension(String extension) {
        return EXTENSION_LANGUAGES.get(extension);
    }
}
