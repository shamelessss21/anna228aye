package ml.pipeline;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Модель объекта данных для preprocessing pipeline.
 */
public class DataPoint {
    private final int id;
    private final Map<String, Double> features;

    public DataPoint(int id, Map<String, Double> features) {
        this.id = id;
        this.features = new HashMap<>(features);
    }

    public int getId() {
        return id;
    }

    public Map<String, Double> getFeatures() {
        return Collections.unmodifiableMap(features);
    }

    public double getFeatureOrDefault(String featureName, double defaultValue) {
        return features.getOrDefault(featureName, defaultValue);
    }

    @Override
    public String toString() {
        return "DataPoint{" +
                "id=" + id +
                ", features=" + features +
                '}';
    }
}
