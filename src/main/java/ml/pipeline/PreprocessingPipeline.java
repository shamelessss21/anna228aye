package ml.pipeline;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Полный pipeline:
 * 1) Индексация/хранение в деревьях и хеш-таблицах.
 * 2) Поиск аномалий.
 * 3) Удаление аномалий из данных.
 * 4) Кластеризация оставшихся объектов через граф.
 */
public class PreprocessingPipeline {
    private final FeatureStore featureStore;
    private final AnomalyDetector anomalyDetector;
    private final GraphClusterer graphClusterer;

    public PreprocessingPipeline() {
        this.featureStore = new FeatureStore();
        this.anomalyDetector = new AnomalyDetector();
        this.graphClusterer = new GraphClusterer();
    }

    public PipelineResult run(List<DataPoint> data,
                              String anomalyFeature,
                              double zThreshold,
                              double clusteringEpsilon) {
        featureStore.clear();

        for (DataPoint point : data) {
            featureStore.add(point);
        }

        List<DataPoint> anomalies = anomalyDetector.detectByZScore(featureStore.getAll(), anomalyFeature, zThreshold);
        Set<String> anomalyIds = anomalies.stream().map(DataPoint::getId).collect(Collectors.toCollection(HashSet::new));

        List<DataPoint> cleanData = featureStore.getAll().stream()
                .filter(point -> !anomalyIds.contains(point.getId()))
                .collect(Collectors.toList());

        List<List<DataPoint>> clusters = graphClusterer.cluster(cleanData, clusteringEpsilon);

        return new PipelineResult(featureStore, anomalies, cleanData, clusters);
    }
}
