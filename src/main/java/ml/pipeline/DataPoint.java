package ml.pipeline;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Один объект набора данных с меткой и набором числовых признаков.
 */
public class DataPoint {
    private final String id;
    private final Map<String, Double> features;

    public DataPoint(String id, Map<String, Double> features) {
        this.id = id;
        this.features = new HashMap<>(features);
    }

    public String getId() {
        return id;
    }

    public Map<String, Double> getFeatures() {
        return Collections.unmodifiableMap(features);
    }

    public double getFeature(String featureName) {
        Double value = features.get(featureName);
        if (value == null) {
            throw new IllegalArgumentException("Признак не найден: " + featureName);
        }
        return value;
    }

    @Override
    public String toString() {
        return "DataPoint{" +
                "id='" + id + '\'' +
                ", features=" + features +
                '}';
    }
}
