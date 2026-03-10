package ml.pipeline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FeatureNormalizer {

    public List<DataPoint> zNormalize(List<DataPoint> points) {
        if (points.isEmpty()) {
            return List.of();
        }

        Map<String, Double> means = new HashMap<>();
        Map<String, Double> stds = new HashMap<>();

        for (String feature : points.get(0).getFeatures().keySet()) {
            double mean = points.stream().mapToDouble(p -> p.getFeature(feature)).average().orElse(0.0);
            double variance = points.stream()
                    .mapToDouble(p -> {
                        double diff = p.getFeature(feature) - mean;
                        return diff * diff;
                    })
                    .average()
                    .orElse(0.0);

            means.put(feature, mean);
            stds.put(feature, Math.sqrt(variance));
        }

        List<DataPoint> normalized = new ArrayList<>();
        for (DataPoint point : points) {
            Map<String, Double> values = new LinkedHashMap<>();
            for (Map.Entry<String, Double> entry : point.getFeatures().entrySet()) {
                String feature = entry.getKey();
                double std = stds.getOrDefault(feature, 0.0);
                if (std == 0.0) {
                    values.put(feature, 0.0);
                } else {
                    values.put(feature, (entry.getValue() - means.get(feature)) / std);
                }
            }
            normalized.add(new DataPoint(point.getId(), values));
        }

        return normalized;
    }
}
