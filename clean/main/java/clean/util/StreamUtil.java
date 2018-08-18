package clean.util;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamUtil {
    public static <T> Stream<T> stream(Iterable<T> iterable) {
        if (iterable == null) {
            return null;
        }
        return StreamSupport.stream(iterable.spliterator(), false);
    }
}
