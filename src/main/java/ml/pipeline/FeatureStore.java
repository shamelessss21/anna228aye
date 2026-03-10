package ml.pipeline;

import java.util.Collection;

/**
 * Базовый контракт хранилища объектов.
 */
public interface FeatureStore {
    void add(DataPoint point);

    DataPoint get(int id);

    Collection<DataPoint> getAll();
}
