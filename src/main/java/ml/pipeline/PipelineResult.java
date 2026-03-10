package ml.pipeline;

import java.util.List;

public class PipelineResult {
    private final FeatureStore featureStore;
    private final List<DataPoint> anomalies;
    private final List<DataPoint> cleanData;
    private final List<List<DataPoint>> clusters;
    private final int inputCount;
    private final int anomalyCount;
    private final int cleanCount;
    private final int clusterCount;
    private final List<Integer> clusterSizes;

    public PipelineResult(FeatureStore featureStore,
                          List<DataPoint> anomalies,
                          List<DataPoint> cleanData,
                          List<List<DataPoint>> clusters,
                          int inputCount,
                          List<Integer> clusterSizes) {
        this.featureStore = featureStore;
        this.anomalies = anomalies;
        this.cleanData = cleanData;
        this.clusters = clusters;
        this.inputCount = inputCount;
        this.anomalyCount = anomalies.size();
        this.cleanCount = cleanData.size();
        this.clusterCount = clusters.size();
        this.clusterSizes = clusterSizes;
    }

    public FeatureStore getFeatureStore() {
        return featureStore;
    }

    public List<DataPoint> getAnomalies() {
        return anomalies;
    }

    public List<DataPoint> getCleanData() {
        return cleanData;
    }

    public List<List<DataPoint>> getClusters() {
        return clusters;
    }

    public int getInputCount() {
        return inputCount;
    }

    public int getAnomalyCount() {
        return anomalyCount;
    }

    public int getCleanCount() {
        return cleanCount;
    }

    public int getClusterCount() {
        return clusterCount;
    }

    public List<Integer> getClusterSizes() {
        return clusterSizes;
    }
}
