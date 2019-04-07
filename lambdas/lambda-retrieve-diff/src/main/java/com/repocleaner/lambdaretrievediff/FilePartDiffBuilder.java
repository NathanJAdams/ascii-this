package com.repocleaner.lambdaretrievediff;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch;

@RequiredArgsConstructor
@ToString
public class FilePartDiffBuilder {
    private static final DiffMatchPatch DMP = new DiffMatchPatch();

    private final List<String> aList = new ArrayList<>();
    private final List<String> bList = new ArrayList<>();
    private final List<String> commonList = new ArrayList<>();
    private StringBuilder a = new StringBuilder();
    private StringBuilder b = new StringBuilder();
    private StringBuilder common = new StringBuilder();

    public void reset() {
        aList.clear();
        bList.clear();
        commonList.clear();
        this.a.setLength(0);
        this.b.setLength(0);
        this.common.setLength(0);
    }

    public void appendA(String a) {
        finishCommon();
        appendLine(this.a, a);
    }

    public void appendB(String b) {
        finishCommon();
        appendLine(this.b, b);
    }

    public void appendCommon(String common) {
        finishAB();
        appendLine(this.common, common);
    }

    private void appendLine(StringBuilder sb, String inputLine) {
        String line = inputLine.substring(1);
        sb.append(line);
        sb.append('\n');
    }

    private void finishAB() {
        if (a.length() > 0 || b.length() > 0) {
            String aString = a.toString();
            String bString = b.toString();
            List<DiffMatchPatch.Diff> diffs = DMP.diffMain(aString, bString);
            for (DiffMatchPatch.Diff diff : diffs) {
                List<String> list;
                if (diff.operation == DiffMatchPatch.Operation.DELETE) {
                    list = aList;
                } else if (diff.operation == DiffMatchPatch.Operation.INSERT) {
                    list = bList;
                } else {
                    aList.add(null);
                    bList.add(null);
                    list = commonList;
                }
                list.add(diff.text);
            }
            a.setLength(0);
            b.setLength(0);
        }
    }

    private void finishCommon() {
        if (common.length() > 0) {
            aList.add(null);
            bList.add(null);
            commonList.add(common.toString());
            common.setLength(0);
        }
    }

    public boolean isReset() {
        return ((a.length() == 0)
                && (b.length() == 0)
                && (common.length() == 0)
                && aList.isEmpty()
                && bList.isEmpty()
                && commonList.isEmpty());
    }

    public FilePartDiff build() {
        finishAB();
        finishCommon();
        return new FilePartDiff(aList.toArray(new String[0]), bList.toArray(new String[0]), commonList.toArray(new String[0]));
    }
}
