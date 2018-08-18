package clean.transform.coster;

import clean.transform.Transformation;
import clean.transform.TransformationCoster;

public class PlainCoster implements TransformationCoster {
    @Override
    public int calculateTokenCost(Transformation transformation) {
        return (2 * transformation.getAdded())
                + transformation.getRemoved()
                + (3 * transformation.getChanged())
                + transformation.getMoved();
    }
}
