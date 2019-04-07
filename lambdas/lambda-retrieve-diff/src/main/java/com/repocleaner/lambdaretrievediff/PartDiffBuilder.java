package com.repocleaner.lambdaretrievediff;

public class PartDiffBuilder {
    private final StringBuilder currentA = new StringBuilder();
    private final StringBuilder currentB = new StringBuilder();

    public void reset() {
        currentA.setLength(0);
        currentB.setLength(0);
    }

    public void appendA(String a) {
        currentA.append(a, 1, a.length());
        currentA.append('\n');
    }

    public void appendB(String b) {
        currentB.append(b, 1, b.length());
        currentB.append('\n');
    }

    public boolean isReset() {
        return ((currentA.length() == 0)
                && (currentB.length() == 0));
    }

    public PartDiff build() {
        return new PartDiff(currentA.toString(), currentB.toString());
    }
}
