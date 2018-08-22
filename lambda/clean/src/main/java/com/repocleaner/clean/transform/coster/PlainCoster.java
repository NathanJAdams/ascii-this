package com.repocleaner.clean.transform.coster;

import com.repocleaner.clean.transform.Transformation;
import com.repocleaner.clean.transform.TransformationCoster;

public class PlainCoster implements TransformationCoster {
    @Override
    public int calculateTokenCost(Transformation transformation) {
        return (2 * transformation.getAdded())
                + transformation.getRemoved()
                + (3 * transformation.getChanged())
                + transformation.getMoved();
    }
}
