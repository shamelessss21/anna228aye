package ml.pipeline;

import java.util.ArrayList;
import java.util.List;

/**
 * Поиск аномалий методом Z-score по одному выбранному признаку.
 */
public class AnomalyDetector {

    public List<DataPoint> detectByZScore(List<DataPoint> points, String featureName, double threshold) {
        List<DataPoint> anomalies = new ArrayList<>();
        if (points.isEmpty()) {
            return anomalies;
        }

        double mean = points.stream().mapToDouble(p -> p.getFeature(featureName)).average().orElse(0.0);
        double variance = points.stream()
                .mapToDouble(p -> {
                    double diff = p.getFeature(featureName) - mean;
                    return diff * diff;
                })
                .average()
                .orElse(0.0);

        double std = Math.sqrt(variance);
        if (std == 0.0) {
            return anomalies;
        }

        for (DataPoint point : points) {
            double z = Math.abs((point.getFeature(featureName) - mean) / std);
            if (z > threshold) {
                anomalies.add(point);
            }
        }
        return anomalies;
    }
}
