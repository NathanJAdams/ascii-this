package com.repocleaner.lambdaretrievediff;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DiffBuilder {
    private final List<FileDiff> fileDiffs = new ArrayList<>();

    public void reset() {
        fileDiffs.clear();
    }

    public void appendFileDiff(FileDiff fileDiff) {
        this.fileDiffs.add(fileDiff);
    }

    public boolean isReset() {
        return fileDiffs.isEmpty();
    }

    public Diff build() {
        return new Diff(fileDiffs.toArray(new FileDiff[0]));
    }
}
