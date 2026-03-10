package ml.pipeline;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Детектор аномалий на основе Z-score по всем числовым признакам.
 */
public class AnomalyDetector {
    private final Map<String, Double> means = new HashMap<>();
    private final Map<String, Double> stdDevs = new HashMap<>();
    private final double threshold;

    public AnomalyDetector(double threshold) {
        this.threshold = threshold;
    }

    public void fit(Collection<DataPoint> points) {
        means.clear();
        stdDevs.clear();

        if (points.isEmpty()) {
            return;
        }

        Map<String, Double> sums = new HashMap<>();
        int n = points.size();

        for (DataPoint point : points) {
            for (Map.Entry<String, Double> feature : point.getFeatures().entrySet()) {
                sums.merge(feature.getKey(), feature.getValue(), Double::sum);
            }
        }

        for (Map.Entry<String, Double> entry : sums.entrySet()) {
            means.put(entry.getKey(), entry.getValue() / n);
        }

        Map<String, Double> sqDiffs = new HashMap<>();
        for (DataPoint point : points) {
            for (Map.Entry<String, Double> feature : point.getFeatures().entrySet()) {
                String name = feature.getKey();
                double diff = feature.getValue() - means.getOrDefault(name, 0.0);
                sqDiffs.merge(name, diff * diff, Double::sum);
            }
        }

        for (Map.Entry<String, Double> entry : sqDiffs.entrySet()) {
            stdDevs.put(entry.getKey(), Math.sqrt(entry.getValue() / n));
        }
    }

    public boolean isAnomaly(DataPoint point) {
        for (Map.Entry<String, Double> feature : point.getFeatures().entrySet()) {
            String name = feature.getKey();
            Double mean = means.get(name);
            Double std = stdDevs.get(name);
            if (mean == null || std == null || std == 0.0) {
                continue;
            }

            double z = Math.abs((feature.getValue() - mean) / std);
            if (z > threshold) {
                return true;
            }
        }
        return false;
    }

    public List<DataPoint> detect(Collection<DataPoint> points) {
        List<DataPoint> anomalies = new ArrayList<>();
        for (DataPoint point : points) {
            if (isAnomaly(point)) {
                anomalies.add(point);
            }
        }
        return anomalies;
    }
}
