package ml.pipeline;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Единый pipeline preprocessing:
 * 1) загрузка в хранилище/индекс,
 * 2) поиск аномалий,
 * 3) очистка,
 * 4) графовая кластеризация.
 */
public class PreprocessingPipeline {
    private final FeatureStore featureStore;
    private final FeatureRangeIndex rangeIndex;
    private final AnomalyDetector anomalyDetector;
    private final GraphBuilder graphBuilder;
    private final GraphClusterer graphClusterer;

    public PreprocessingPipeline(FeatureStore featureStore,
                                 AnomalyDetector anomalyDetector,
                                 GraphBuilder graphBuilder,
                                 GraphClusterer graphClusterer) {
        this.featureStore = featureStore;
        this.rangeIndex = new FeatureRangeIndex(featureStore);
        this.anomalyDetector = anomalyDetector;
        this.graphBuilder = graphBuilder;
        this.graphClusterer = graphClusterer;
    }

    public PipelineResult run(List<DataPoint> data) {
        for (DataPoint point : data) {
            featureStore.add(point);
            rangeIndex.add(point);
        }

        anomalyDetector.fit(featureStore.getAll());
        List<DataPoint> anomalies = anomalyDetector.detect(featureStore.getAll());

        Set<Integer> anomalyIds = anomalies.stream().map(DataPoint::getId)
                .collect(Collectors.toCollection(HashSet::new));

        List<DataPoint> cleanData = featureStore.getAll().stream()
                .filter(point -> !anomalyIds.contains(point.getId()))
                .collect(Collectors.toCollection(ArrayList::new));

        Graph graph = graphBuilder.build(cleanData);
        List<Set<Integer>> clusters = graphClusterer.cluster(graph);

        return new PipelineResult(anomalies, cleanData, clusters);
    }

    public List<DataPoint> rangeQuery(String feature, double from, double to) {
        return rangeIndex.rangeQuery(feature, from, to);
    }
}
