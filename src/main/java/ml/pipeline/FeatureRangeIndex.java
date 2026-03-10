package ml.pipeline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * Индекс признаков на деревьях для диапазонных запросов.
 */
public class FeatureRangeIndex {
    private final Map<String, NavigableMap<Double, List<Integer>>> index = new HashMap<>();
    private final FeatureStore store;

    public FeatureRangeIndex(FeatureStore store) {
        this.store = store;
    }

    public void add(DataPoint point) {
        for (Map.Entry<String, Double> entry : point.getFeatures().entrySet()) {
            index.computeIfAbsent(entry.getKey(), k -> new TreeMap<>())
                    .computeIfAbsent(entry.getValue(), v -> new ArrayList<>())
                    .add(point.getId());
        }
    }

    public List<DataPoint> rangeQuery(String feature, double minInclusive, double maxInclusive) {
        List<DataPoint> result = new ArrayList<>();
        NavigableMap<Double, List<Integer>> tree = index.get(feature);
        if (tree == null) {
            return result;
        }

        for (List<Integer> ids : tree.subMap(minInclusive, true, maxInclusive, true).values()) {
            for (Integer id : ids) {
                DataPoint point = store.get(id);
                if (point != null) {
                    result.add(point);
                }
            }
        }
        return result;
    }
}
