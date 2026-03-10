package ml.pipeline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class PreprocessingPipeline {
    private final FeatureStore featureStore;
    private final AnomalyDetector anomalyDetector;
    private final GraphClusterer graphClusterer;
    private final FeatureNormalizer featureNormalizer;

    public PreprocessingPipeline() {
        this.featureStore = new FeatureStore();
        this.anomalyDetector = new AnomalyDetector();
        this.graphClusterer = new GraphClusterer();
        this.featureNormalizer = new FeatureNormalizer();
    }

    public PipelineResult run(List<DataPoint> data,
                              String anomalyFeature,
                              double zThreshold,
                              double clusteringEpsilon) {
        featureStore.clear();

        for (DataPoint point : data) {
            featureStore.add(point);
        }

        List<DataPoint> allPoints = featureStore.getAll();
        List<DataPoint> anomalies = anomalyDetector.detectByZScore(allPoints, anomalyFeature, zThreshold);
        Set<String> anomalyIds = anomalies.stream().map(DataPoint::getId).collect(Collectors.toCollection(HashSet::new));

        List<DataPoint> cleanData = allPoints.stream()
                .filter(point -> !anomalyIds.contains(point.getId()))
                .collect(Collectors.toList());

        List<DataPoint> normalizedCleanData = featureNormalizer.zNormalize(cleanData);
        List<List<DataPoint>> normalizedClusters = graphClusterer.cluster(normalizedCleanData, clusteringEpsilon);

        Map<String, DataPoint> cleanById = new HashMap<>();
        for (DataPoint point : cleanData) {
            cleanById.put(point.getId(), point);
        }

        List<List<DataPoint>> clusters = new ArrayList<>();
        List<Integer> clusterSizes = new ArrayList<>();
        for (List<DataPoint> normalizedCluster : normalizedClusters) {
            List<DataPoint> originalCluster = normalizedCluster.stream()
                    .map(point -> cleanById.get(point.getId()))
                    .collect(Collectors.toList());
            clusters.add(originalCluster);
            clusterSizes.add(originalCluster.size());
        }

        return new PipelineResult(featureStore, anomalies, cleanData, clusters, allPoints.size(), clusterSizes);
    }
}
