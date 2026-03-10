package ml.pipeline;

import java.util.Collection;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * Хранилище на основе TreeMap: упорядочение по id и O(log n)-операции.
 */
public class TreeFeatureStore implements FeatureStore {
    private final NavigableMap<Integer, DataPoint> storage = new TreeMap<>();

    @Override
    public void add(DataPoint point) {
        storage.put(point.getId(), point);
    }

    @Override
    public DataPoint get(int id) {
        return storage.get(id);
    }

    @Override
    public Collection<DataPoint> getAll() {
        return storage.values();
    }
}
