package com.repocleaner.lambdaretrievediff;

import com.repocleaner.util.RepoCleanerException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class DiffExtractor {
    public static Diff extract(String diff) throws RepoCleanerException {
        DiffBuilder diffBuilder = new DiffBuilder();
        FileDiffBuilder fileDiffBuilder = new FileDiffBuilder();
        FilePartDiffBuilder filePartDiffBuilder = new FilePartDiffBuilder();

        try (StringReader sr = new StringReader(diff);
             BufferedReader br = new BufferedReader(sr)) {
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                if (line.length() == 0) {
                    throwError("Empty line", null);
                }
                char firstChar = line.charAt(0);
                if (firstChar == 'd') {
                    finishFilePartDiff(fileDiffBuilder, filePartDiffBuilder);
                    finishFileDiff(diffBuilder, fileDiffBuilder);
                    startFileDiff(br, fileDiffBuilder);
                } else if (firstChar == '@') {
                    finishFilePartDiff(fileDiffBuilder, filePartDiffBuilder);
                    startLineNumbers(line, fileDiffBuilder);
                } else if (firstChar == '-') {
                    filePartDiffBuilder.appendA(line);
                } else if (firstChar == '+') {
                    filePartDiffBuilder.appendB(line);
                } else if (firstChar == ' ') {
                    filePartDiffBuilder.appendCommon(line);
                } else if ("\\ No newline at end of file".equals(line)) {
                    // no newline
                } else {
                    System.out.println(line);
                    throwError("Unknown character '" + firstChar + "'", null);
                }
            }
        } catch (IOException e) {
            throwError("Didn't read diff", e);
        }
        finishFilePartDiff(fileDiffBuilder, filePartDiffBuilder);
        finishFileDiff(diffBuilder, fileDiffBuilder);
        return diffBuilder.build();
    }

    private static void finishFileDiff(DiffBuilder diffBuilder, FileDiffBuilder fileDiffBuilder) {
        if (!fileDiffBuilder.isReset()) {
            FileDiff fileDiff = fileDiffBuilder.build();
            diffBuilder.appendFileDiff(fileDiff);
            fileDiffBuilder.reset();
        }
    }

    private static void startFileDiff(BufferedReader br, FileDiffBuilder fileDiffBuilder) throws RepoCleanerException {
        try {
            String index = br.readLine();
            String fileNameA = br.readLine().substring(6);
            String fileNameB = br.readLine().substring(6);
            fileDiffBuilder.setFileNames(fileNameA, fileNameB);
        } catch (StringIndexOutOfBoundsException e) {
            // TODO delete this block when git diffs are calculated properly
            e.printStackTrace();
            fileDiffBuilder.reset();
        } catch (IOException | NumberFormatException e) {
            throwError("Error in diff format", e);
        }
    }

    private static void startLineNumbers(String line, FileDiffBuilder fileDiffBuilder) throws RepoCleanerException {
        try {
            String[] lineNumberParts = line.split(" ");
            if (lineNumberParts.length != 4) {
                throwError("Error in diff format", null);
            }
            String[] lineNumberPartsFrom = lineNumberParts[1].split(",");
            String[] lineNumberPartsTo = lineNumberParts[2].split(",");
            if ((lineNumberPartsFrom.length == 0) || (lineNumberPartsTo.length == 0)) {
                throwError("Error in diff format", null);
            }
            if ((lineNumberPartsFrom.length > 2) || (lineNumberPartsTo.length > 2)) {
                throwError("Error in diff format", null);
            }
            int fromIndexA = Integer.valueOf(lineNumberPartsFrom[0]);
            int countA = (lineNumberPartsFrom.length == 1)
                    ? 0
                    : Integer.valueOf(lineNumberPartsFrom[1]);
            int fromIndexB = Integer.valueOf(lineNumberPartsTo[0]);
            int countB = (lineNumberPartsTo.length == 1)
                    ? 0
                    : Integer.valueOf(lineNumberPartsTo[1]);
            fileDiffBuilder.setFileLines(fromIndexA, fromIndexB, countA, countB);
        } catch (NumberFormatException e) {
            throwError("Error in diff format", e);
        }
    }

    private static void finishFilePartDiff(FileDiffBuilder fileDiffBuilder, FilePartDiffBuilder filePartDiffBuilder) throws RepoCleanerException {
        if (!filePartDiffBuilder.isReset()) {
            FilePartDiff filePartDiff = filePartDiffBuilder.build();
            fileDiffBuilder.appendFilePartDiff(filePartDiff);
            filePartDiffBuilder.reset();
        }
    }

    private static void throwError(String error, Exception cause) throws RepoCleanerException {
        throw new RepoCleanerException(error, cause);
    }
}
