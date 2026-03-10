package ml.pipeline;

import java.util.List;

public class PipelineResult {
    private final FeatureStore featureStore;
    private final List<DataPoint> anomalies;
    private final List<DataPoint> cleanData;
    private final List<List<DataPoint>> clusters;

    public PipelineResult(FeatureStore featureStore,
                          List<DataPoint> anomalies,
                          List<DataPoint> cleanData,
                          List<List<DataPoint>> clusters) {
        this.featureStore = featureStore;
        this.anomalies = anomalies;
        this.cleanData = cleanData;
        this.clusters = clusters;
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
}
