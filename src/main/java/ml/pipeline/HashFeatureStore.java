package ml.pipeline;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Хранилище на основе HashMap: быстрый доступ по id.
 */
public class HashFeatureStore implements FeatureStore {
    private final Map<Integer, DataPoint> storage = new HashMap<>();

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
