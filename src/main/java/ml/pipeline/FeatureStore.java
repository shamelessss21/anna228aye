package ml.pipeline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Хранилище признаков:
 * - HashMap: быстрый доступ по id объекта.
 * - TreeMap: упорядоченное хранение значений признака для диапазонных запросов.
 */
public class FeatureStore {
    private final Map<String, DataPoint> byId = new HashMap<>();
    private final Map<String, TreeMap<Double, List<String>>> featureIndex = new HashMap<>();

    public void add(DataPoint point) {
        DataPoint previous = byId.get(point.getId());
        if (previous != null) {
            removeFromIndex(previous);
        }

        byId.put(point.getId(), point);
        for (Map.Entry<String, Double> entry : point.getFeatures().entrySet()) {
            featureIndex
                    .computeIfAbsent(entry.getKey(), k -> new TreeMap<>())
                    .computeIfAbsent(entry.getValue(), v -> new ArrayList<>())
                    .add(point.getId());
        }
    }

    public void clear() {
        byId.clear();
        featureIndex.clear();
    }

    public DataPoint getById(String id) {
        return byId.get(id);
    }

    public List<DataPoint> rangeQuery(String featureName, double fromInclusive, double toInclusive) {
        List<DataPoint> result = new ArrayList<>();
        TreeMap<Double, List<String>> tree = featureIndex.get(featureName);
        if (tree == null) {
            return result;
        }

        for (List<String> ids : tree.subMap(fromInclusive, true, toInclusive, true).values()) {
            for (String id : ids) {
                DataPoint point = byId.get(id);
                if (point != null) {
                    result.add(point);
                }
            }
        }
        return result;
    }

    public List<DataPoint> getAll() {
        return new ArrayList<>(byId.values());
    }

    private void removeFromIndex(DataPoint point) {
        for (Map.Entry<String, Double> entry : point.getFeatures().entrySet()) {
            TreeMap<Double, List<String>> tree = featureIndex.get(entry.getKey());
            if (tree == null) {
                continue;
            }

            List<String> ids = tree.get(entry.getValue());
            if (ids == null) {
                continue;
            }

            ids.remove(point.getId());
            if (ids.isEmpty()) {
                tree.remove(entry.getValue());
            }
            if (tree.isEmpty()) {
                featureIndex.remove(entry.getKey());
            }
        }
    }
}
