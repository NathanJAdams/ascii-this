package com.repocleaner.clean.language;

import com.repocleaner.clean.transform.Contextualiser;
import com.repocleaner.clean.transform.java.JavaContextualiser;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Language {
    Java(new JavaParsedFileCreator(), new JavaContextualiser(), "java");
    private static final Map<String, Language> EXTENSION_LANGUAGES = new HashMap<>();

    static {
        for (Language language : values()) {
            for (String extension : language.extensions) {
                EXTENSION_LANGUAGES.put(extension, language);
            }
        }
    }

    private final ParsedFileCreator parsedFileCreator;
    private final Contextualiser contextualiser;
    private final List<String> extensions;

    Language(ParsedFileCreator parsedFileCreator, Contextualiser contextualiser, String... extensions) {
        this.parsedFileCreator = parsedFileCreator;
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

    public ParsedFileCreator getParsedFileCreator() {
        return parsedFileCreator;
    }

    public Contextualiser getContextualiser() {
        return contextualiser;
    }
}
