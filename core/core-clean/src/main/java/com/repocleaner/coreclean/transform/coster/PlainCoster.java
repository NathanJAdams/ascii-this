package com.repocleaner.coreclean.transform.coster;

import com.repocleaner.coreclean.transform.Transformation;
import com.repocleaner.coreclean.transform.TransformationCoster;

public class PlainCoster implements TransformationCoster {
    @Override
    public int calculateTokenCost(Transformation transformation) {
        return (2 * transformation.getAdded())
                + transformation.getRemoved()
                + (3 * transformation.getChanged())
                + transformation.getMoved();
    }
}
