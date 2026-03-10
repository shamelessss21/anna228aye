package ml.pipeline;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Построение графа по евклидову расстоянию между объектами.
 */
public class GraphBuilder {
    private final double epsilon;

    public GraphBuilder(double epsilon) {
        this.epsilon = epsilon;
    }

    public Graph build(Collection<DataPoint> points) {
        Graph graph = new Graph();
        List<DataPoint> list = new ArrayList<>(points);

        for (DataPoint point : list) {
            graph.addNode(point.getId());
        }

        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                DataPoint p1 = list.get(i);
                DataPoint p2 = list.get(j);
                if (euclideanDistance(p1, p2) < epsilon) {
                    graph.addEdge(p1.getId(), p2.getId());
                }
            }
        }
        return graph;
    }

    private double euclideanDistance(DataPoint a, DataPoint b) {
        Set<String> keys = new HashSet<>();
        keys.addAll(a.getFeatures().keySet());
        keys.addAll(b.getFeatures().keySet());

        double sum = 0.0;
        for (String key : keys) {
            double diff = a.getFeatureOrDefault(key, 0.0) - b.getFeatureOrDefault(key, 0.0);
            sum += diff * diff;
        }
        return Math.sqrt(sum);
    }
}
