package ml.pipeline;

import java.util.List;
import java.util.Set;

public class PipelineResult {
    private final List<DataPoint> anomalies;
    private final List<DataPoint> cleanData;
    private final List<Set<Integer>> clusters;

    public PipelineResult(List<DataPoint> anomalies, List<DataPoint> cleanData, List<Set<Integer>> clusters) {
        this.anomalies = anomalies;
        this.cleanData = cleanData;
        this.clusters = clusters;
    }

    public List<DataPoint> getAnomalies() {
        return anomalies;
    }

    public List<DataPoint> getCleanData() {
        return cleanData;
    }

    public List<Set<Integer>> getClusters() {
        return clusters;
    }
}
