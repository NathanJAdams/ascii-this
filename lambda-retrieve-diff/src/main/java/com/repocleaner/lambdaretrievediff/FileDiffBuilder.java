package com.repocleaner.lambdaretrievediff;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class FileDiffBuilder {
    private final List<FilePartDiff> filePartDiffs = new ArrayList<>();
    private String fileNameA;
    private String fileNameB;
    private int fromIndexA = -1;
    private int fromIndexB = -1;
    private int countA = -1;
    private int countB = -1;

    public void reset() {
        filePartDiffs.clear();
        this.fileNameA = null;
        this.fileNameB = null;
        this.fromIndexA = -1;
        this.fromIndexB = -1;
        this.countA = -1;
        this.countB = -1;
    }

    public void setFileNames(String fileNameA, String fileNameB) {
        this.fileNameA = fileNameA;
        this.fileNameB = fileNameB;
    }

    public void setFileLines(int fromIndexA, int fromIndexB, int countA, int countB) {
        this.fromIndexA = fromIndexA;
        this.fromIndexB = fromIndexB;
        this.countA = countA;
        this.countB = countB;
    }

    public void appendFilePartDiff(FilePartDiff filePartDiff) {
        this.filePartDiffs.add(filePartDiff);
    }

    public boolean isReset() {
        return ((fileNameA == null)
                && (fileNameB == null)
                && (fromIndexA == -1)
                && (fromIndexB == -1)
                && (countA == -1)
                && (countB == -1)
                && filePartDiffs.isEmpty());
    }

    public FileDiff build() {
        return new FileDiff(fileNameA, fileNameB, filePartDiffs.toArray(new FilePartDiff[0]));
    }
}
