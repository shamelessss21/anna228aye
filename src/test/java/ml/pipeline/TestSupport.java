package ml.pipeline;

import java.util.Objects;

public class TestSupport {
    public static void assertEquals(Object expected, Object actual, String message) {
        if (!Objects.equals(expected, actual)) {
            throw new AssertionError(message + ". expected=" + expected + ", actual=" + actual);
        }
    }
}
